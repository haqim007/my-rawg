package dev.haqim.myrawg.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.haqim.myrawg.data.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey:List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys where id = :id")
    suspend fun getRemoteKeyById(id: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}