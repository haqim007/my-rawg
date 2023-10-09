package dev.haqim.myrawg.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.haqim.myrawg.data.local.entity.GAME_LIST_ITEM_TABLE
import dev.haqim.myrawg.data.local.entity.GamesListItemEntity

@Dao
interface GamesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        games: List<GamesListItemEntity>
    )
    
    @Query("SELECT * FROM $GAME_LIST_ITEM_TABLE ORDER BY addedToPublicCollection DESC")
    fun getAll(): PagingSource<Int, GamesListItemEntity>

    @Query("SELECT * FROM $GAME_LIST_ITEM_TABLE WHERE title like :query ORDER BY addedToPublicCollection DESC")
    fun search(query: String): PagingSource<Int, GamesListItemEntity>

    @Query("DELETE FROM $GAME_LIST_ITEM_TABLE")
    suspend fun clearAll()
}