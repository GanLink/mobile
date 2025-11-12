package com.ganlink.pe.features.farmmanagement.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farms")
data class FarmEntity(
    @PrimaryKey val id: Int,
    val alias: String,
    val description: String,
    val mainActivity: String,
    val ownerDni: String,
    val userId: Int
)
