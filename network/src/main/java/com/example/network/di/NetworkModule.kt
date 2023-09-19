package com.example.network.di

import com.example.data.source.network.fact.FactNetworkDataSource
import com.example.network.apiservice.FactApiService
import com.example.network.source.DefaultFactNetworkDataSource
import com.example.network.utils.Extensions.moshi
import com.example.network.utils.NetworkConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun provideFactNetworkDataSource(
        datasource: DefaultFactNetworkDataSource
    ): FactNetworkDataSource

    companion object {

        private const val timeout: Long = 30

        @Provides
        @Singleton
        fun provideFactApi(builder: Retrofit.Builder): FactApiService {
            return builder
                .build()
                .create(FactApiService::class.java)
        }

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
        }

        @Provides
        fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
    }
}
