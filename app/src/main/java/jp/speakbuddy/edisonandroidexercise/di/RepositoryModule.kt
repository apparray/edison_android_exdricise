package jp.speakbuddy.edisonandroidexercise.di

import android.app.Application
import android.content.Context
import com.example.data.FactRepositoryImpl
import com.example.data.repository.dataSource.FactRemoteDataSource
import com.example.domain.repository.FactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideFactsRepository(factRemoteDataSource: FactRemoteDataSource, application: Application) : FactRepository=
        FactRepositoryImpl(factRemoteDataSource, application)
}
