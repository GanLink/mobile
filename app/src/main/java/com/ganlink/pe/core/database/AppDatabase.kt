package com.ganlink.pe.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.auth.data.local.models.UserEntity
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}