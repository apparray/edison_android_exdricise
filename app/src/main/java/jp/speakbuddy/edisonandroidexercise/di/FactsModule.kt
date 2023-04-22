package jp.speakbuddy.edisonandroidexercise.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides

import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.FactRepositoryImpl
import jp.speakbuddy.edisonandroidexercise.repository.RandomManager
import jp.speakbuddy.edisonandroidexercise.repository.RandomManagerImpl
import jp.speakbuddy.edisonandroidexercise.repository.local.FactLocalDataStore
import jp.speakbuddy.edisonandroidexercise.repository.local.FactLocalDataStoreImpl
import jp.speakbuddy.edisonandroidexercise.repository.network.FactService
import jp.speakbuddy.edisonandroidexercise.repository.network.FactsSerializer
import jp.speakbuddy.edisonandroidexercise.repository.remote.FactRemoteDataStore
import jp.speakbuddy.edisonandroidexercise.repository.remote.FactRemoteDataStoreImpl
import jp.speakbuddy.edisonandroidexercise.ui.fact.usecases.FactUseCases
import jp.speakbuddy.edisonandroidexercise.ui.fact.usecases.FactUseCasesImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactsModule {
    private const val PREFERENCE_NAME = "facts"

    @Singleton
    @Provides
    fun provideFactLocalDataStore(@ApplicationContext context: Context): FactLocalDataStore {
        return FactLocalDataStoreImpl(
            DataStoreFactory.create(
                serializer = FactsSerializer,
                produceFile = { context.dataStoreFile(PREFERENCE_NAME) },
                corruptionHandler = null
            )
        )
    }

    @Singleton
    @Provides
    fun provideRemoteDataStore(
        factProvider: FactService,
        dispatcher: CoroutineDispatcher
    ): FactRemoteDataStore {
        return FactRemoteDataStoreImpl(factProvider, dispatcher)
    }

    @Singleton
    @Provides
    fun provideFactRepository(
        remoteDataStore: FactRemoteDataStore,
        locaDataStore: FactLocalDataStore,
        random: RandomManager
    ): FactRepository {
        return FactRepositoryImpl(remoteDataStore, locaDataStore, random)
    }

    @Singleton
    @Provides
    fun provideCoroutineCoroutineScope(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideFactsService(retrofit: Retrofit): FactService {
        return retrofit.create(FactService::class.java)
    }

    @Singleton
    @Provides
    fun provideRandom(): RandomManager {
        return RandomManagerImpl()
    }

    @Singleton
    @Provides
    fun provideFactUseCases(repository: FactRepository): FactUseCases {
        return FactUseCasesImpl(repository)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()


}