package com.ganlink.pe.features.auth.di

import com.ganlink.pe.features.auth.data.repositories.LoginRepositoryImpl
import com.ganlink.pe.features.auth.domain.repositories.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface LoginModule {
    @Binds
    fun providesLoginRepository(impl: LoginRepositoryImpl) : LoginRepository
}