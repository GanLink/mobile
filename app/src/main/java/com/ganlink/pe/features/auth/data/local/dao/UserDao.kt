package com.ganlink.pe.features.auth.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ganlink.pe.features.auth.data.local.models.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(vararg entity: UserEntity)

    @Delete
    suspend fun delete(vararg entity: UserEntity)

    @Update
    suspend fun update(vararg entity: UserEntity)

    @Query("select * from users")
    suspend fun fetchAllUsers(): List<UserEntity>

    @Query("select * from users where id=:id")
    suspend fun fetchUserById(id: Int): List<UserEntity>
}