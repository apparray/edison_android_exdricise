package com.example.network.di

import com.example.data.connectivity.ConnectivityChecker
import com.example.network.connectivity.DefaultConnectivityChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityChecker(
        connectivityChecker: DefaultConnectivityChecker
    ): ConnectivityChecker
}
