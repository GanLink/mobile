package com.ganlink.pe.features.bovinuesystem.di

import com.ganlink.pe.features.bovinuesystem.data.remote.BovinueSystemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BovinueSystemModule {
    @Provides
    @Singleton
    fun provideBovinueSystemService(retrofit: Retrofit): BovinueSystemService {
        return retrofit.create(BovinueSystemService::class.java)
    }
}
