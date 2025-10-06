package com.ganlink.pe.features.auth.di

import com.ganlink.pe.core.networking.ApiConstModule
import com.ganlink.pe.features.auth.data.remote.services.RegisterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
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
    fun providesRegisterService(retrofit: Retrofit) : RegisterService{
        return retrofit.create(RegisterService::class.java)
    }
}