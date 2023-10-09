package dev.haqim.myrawg.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.haqim.myrawg.data.local.entity.GamesCollectionEntity
import dev.haqim.myrawg.data.local.entity.toModel
import dev.haqim.myrawg.data.util.DEFAULT_PAGE_SIZE
import dev.haqim.myrawg.domain.model.GamesListItem
import retrofit2.HttpException
import java.io.IOException

class GameCollectionsPagingSource (
    private val getCollection: suspend (limit: Int, offset: Int) -> List<GamesCollectionEntity>,
): PagingSource<Int, GamesListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesListItem> {

        val position = params.key ?: 0
        val offset = position * DEFAULT_PAGE_SIZE

        return try {
            val gameCollections = getCollection(DEFAULT_PAGE_SIZE, offset).toModel()

            val nextKey = if (gameCollections.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / DEFAULT_PAGE_SIZE)
            }
            LoadResult.Page(
                data = gameCollections,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, GamesListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
