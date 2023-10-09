package dev.haqim.myrawg.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.haqim.myrawg.data.local.entity.GAME_DETAIL_TABLE
import dev.haqim.myrawg.data.local.entity.GameDetailEntity
import dev.haqim.myrawg.data.local.entity.GameDetailWithCollectionStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        game: GameDetailEntity
    )
    
    @Transaction
    @Query("SELECT * FROM $GAME_DETAIL_TABLE WHERE id = :id")
    fun getById(id: Int): Flow<GameDetailWithCollectionStatusEntity?>

}