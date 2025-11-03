package com.nkot117.core.data.di

import com.nkot117.core.data.repository.ItemsRepositoryImpl
import com.nkot117.core.data.repository.SpecialItemDateRepositoryImpl
import com.nkot117.core.domain.repository.ItemsRepository
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindItemsRepository(
        impl: ItemsRepositoryImpl
    ): ItemsRepository

    @Binds
    @Singleton
    abstract fun bindSpecialDatesRepository(
        impl: SpecialItemDateRepositoryImpl
    ): SpecialItemDateRepository
}