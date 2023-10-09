package dev.haqim.myrawg.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.haqim.myrawg.data.local.entity.GAMES_COLLECTION_TABLE
import dev.haqim.myrawg.data.local.entity.GamesCollectionEntity

@Dao
interface GameCollectionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        game: GamesCollectionEntity
    )
    
    @Query("SELECT * FROM $GAMES_COLLECTION_TABLE LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): List<GamesCollectionEntity>

    @Query("SELECT * FROM $GAMES_COLLECTION_TABLE WHERE title like :query LIMIT :limit OFFSET :offset")
    suspend fun search(query: String, limit: Int, offset: Int): List<GamesCollectionEntity>

    @Query("DELETE FROM $GAMES_COLLECTION_TABLE where gameId = :id")
    suspend fun remove(id: Int)
}