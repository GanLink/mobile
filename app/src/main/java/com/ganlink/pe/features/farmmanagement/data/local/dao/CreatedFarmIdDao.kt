package com.ganlink.pe.features.farmmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ganlink.pe.features.farmmanagement.data.local.models.CreatedFarmIdEntity

@Dao
interface CreatedFarmIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CreatedFarmIdEntity)

    @Query("SELECT * FROM created_farm_ids LIMIT 1")
    suspend fun fetchLastCreated(): CreatedFarmIdEntity?

    @Query("DELETE FROM created_farm_ids WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM created_farm_ids")
    suspend fun clearAll()
}
