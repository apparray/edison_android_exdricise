package jp.speakbuddy.edisonandroidexercise.di

import com.example.data.api.FactApi
import com.example.data.repository.dataSource.FactRemoteDataSource
import com.example.data.repository.dataSourceImpl.FactRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    fun provideFactsRemoteDataSource(factApi: FactApi) : FactRemoteDataSource =
        FactRemoteDataSourceImpl(factApi)
}