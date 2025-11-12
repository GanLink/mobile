package com.ganlink.pe.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.auth.data.local.models.UserEntity
import com.ganlink.pe.features.farmmanagement.data.local.dao.CreatedFarmIdDao
import com.ganlink.pe.features.farmmanagement.data.local.dao.FarmDao
import com.ganlink.pe.features.farmmanagement.data.local.models.CreatedFarmIdEntity
import com.ganlink.pe.features.farmmanagement.data.local.models.FarmEntity
@Database(
    entities = [UserEntity::class, FarmEntity::class, CreatedFarmIdEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun farmDao(): FarmDao
    abstract fun createdFarmIdDao(): CreatedFarmIdDao
}
