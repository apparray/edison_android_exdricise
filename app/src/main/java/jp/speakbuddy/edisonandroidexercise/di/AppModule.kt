package jp.speakbuddy.edisonandroidexercise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.dataStore
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideFactService(retrofit: Retrofit): FactService = retrofit.create(FactService::class.java)

    @Singleton
    @Provides
    fun providePreferencesDataStore(context: Context): DataStore<Preferences> = context.dataStore

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context
}

