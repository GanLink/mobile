package com.ganlink.pe.features.farmmanagement.di

import com.ganlink.pe.features.farmmanagement.data.repository.FarmMangementRepositoryImpl
import com.ganlink.pe.features.farmmanagement.domain.repositories.FarmMangementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FarmManagementModule {
    @Binds
    fun bindsFarmRepository(impl: FarmMangementRepositoryImpl) : FarmMangementRepository
}