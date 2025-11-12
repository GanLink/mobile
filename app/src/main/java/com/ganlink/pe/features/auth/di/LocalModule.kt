package com.ganlink.pe.features.auth.di

import android.content.Context
import androidx.room.Room
import com.ganlink.pe.core.database.AppDatabase
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.farmmanagement.data.local.dao.CreatedFarmIdDao
import com.ganlink.pe.features.farmmanagement.data.local.dao.FarmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            klass = AppDatabase::class.java,
            "ganlink"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun providesUserDao(db : AppDatabase) : UserDao{
        return db.userDao()
    }

    @Provides
    @Singleton
    fun providesFarmDao(db: AppDatabase): FarmDao {
        return db.farmDao()
    }

    @Provides
    @Singleton
    fun providesCreatedFarmIdDao(db: AppDatabase): CreatedFarmIdDao {
        return db.createdFarmIdDao()
    }
}
