package com.ganlink.pe.features.bovinuesystem.di

import com.ganlink.pe.features.bovinuesystem.data.repositories.BovinueRepositoryImpl
import com.ganlink.pe.features.bovinuesystem.domain.repositories.BovinueRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface BovinueRepositoryModule {
    @Binds
    fun bindBovinueRepository(impl: BovinueRepositoryImpl): BovinueRepository
}
