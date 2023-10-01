package jp.speakbuddy.edisonandroidexercise.fact.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.fact.data.local.FactDataStore
import jp.speakbuddy.edisonandroidexercise.fact.data.remote.FactApi
import jp.speakbuddy.edisonandroidexercise.fact.data.repository.FactRepositoryImpl
import jp.speakbuddy.edisonandroidexercise.fact.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.fact.domain.usecase.CheckFactContentUseCase
import jp.speakbuddy.edisonandroidexercise.fact.domain.usecase.CheckFactLengthUseCase
import jp.speakbuddy.edisonandroidexercise.fact.domain.usecase.GetFactUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatFactModule {

    @Provides
    @Singleton
    fun provideGetFactUseCase(repository: FactRepository): GetFactUseCase {
        return GetFactUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFactContainsKeywordUseCase(): CheckFactContentUseCase {
        return CheckFactContentUseCase()
    }

    @Provides
    @Singleton
    fun provideFactExceedsLengthUseCase(): CheckFactLengthUseCase {
        return CheckFactLengthUseCase()
    }

    @Provides
    @Singleton
    fun provideFactRepository(
        api: FactApi,
        dataStore: FactDataStore
    ): FactRepository {
        return FactRepositoryImpl(api, dataStore)
    }

    @Singleton
    @Provides
    fun provideFactDataStore(
        @ApplicationContext app: Context
    ) = FactDataStore(app)

    @Provides
    @Singleton
    fun provideFactApi(): FactApi {
        return Retrofit.Builder()
            .baseUrl(FactApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactApi::class.java)
    }
}