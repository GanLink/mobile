package com.ganlink.pe.features.farmmanagement.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "created_farm_ids")
data class CreatedFarmIdEntity(
    @PrimaryKey val id: Int
)
