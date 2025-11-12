package com.ganlink.pe.features.farmmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ganlink.pe.features.farmmanagement.data.local.models.FarmEntity

@Dao
interface FarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(farm: FarmEntity)

    @Query("DELETE FROM farms WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM farms WHERE id = :id LIMIT 1")
    suspend fun fetchById(id: Int): FarmEntity?

    @Query("DELETE FROM farms")
    suspend fun clearAll()
}
