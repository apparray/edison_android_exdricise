package com.example.data.di

import com.example.data.repository.DefaultFactRepository
import com.example.domain.repository.fact.FactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideFactRepository(
        repository: DefaultFactRepository
    ): FactRepository

}
