package com.ganlink.pe.features.farmmanagement.di

import com.ganlink.pe.core.networking.ApiConstModule
import com.ganlink.pe.features.auth.data.remote.services.LoginService
import com.ganlink.pe.features.auth.data.remote.services.RegisterService
import com.ganlink.pe.features.farmmanagement.data.remote.FarmManagementService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FarmModule {
    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(ApiConstModule.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providesFarmService(retrofit : Retrofit) : FarmManagementService{
        return retrofit.create(FarmManagementService::class.java)
    }

}