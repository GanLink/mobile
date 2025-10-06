package com.ganlink.pe.features.auth.di

import com.ganlink.pe.features.auth.data.repositories.RegisterRepositoryImpl
import com.ganlink.pe.features.auth.domain.repositories.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRegisterRepository(impl : RegisterRepositoryImpl) : RegisterRepository
}