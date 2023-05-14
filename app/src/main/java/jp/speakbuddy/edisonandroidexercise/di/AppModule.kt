package jp.speakbuddy.edisonandroidexercise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.speakbuddy.edisonandroidexercise.BuildConfig
import jp.speakbuddy.edisonandroidexercise.Constants.DataStore.FACT_PREFERENCES
import jp.speakbuddy.edisonandroidexercise.Constants.DataStore.FACT_PREFERENCES_KEY
import jp.speakbuddy.edisonandroidexercise.Constants.Koin.FACT_HTTPS
import jp.speakbuddy.edisonandroidexercise.Constants.Koin.FACT_RF
import jp.speakbuddy.edisonandroidexercise.data.local.PersistentStorage
import jp.speakbuddy.edisonandroidexercise.data.local.`interface`.Storage
import jp.speakbuddy.edisonandroidexercise.data.network.api.FactAPI
import jp.speakbuddy.edisonandroidexercise.data.network.interceptor.interceptor
import jp.speakbuddy.edisonandroidexercise.data.network.view_data.Fact
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FACT_PREFERENCES)

val dataStore = module {
    single<Storage<Fact>> {
        PersistentStorage(
            gson = get(),
            type = object : TypeToken<List<Fact>>() {}.type,
            preferenceKey = stringPreferencesKey(FACT_PREFERENCES_KEY),
            dataStore = androidContext().dataStore
        )
    }
}

val dispatcherModule = module {
    single {
        Dispatchers.IO
    }
}

val viewModelModule = module {
    viewModel { FactViewModel(factRepository = get(), dispatcher = get(), factStorage = get()) }
}

val repositoryModule = module {
    single {
        FactRepository(factAPI = get())
    }
}

val retrofitModule = module {
    single {
        Gson()
    }

    single {
        interceptor
    }

    single(named(FACT_HTTPS)) {
        OkHttpClient.Builder()
            .addInterceptor(get() as HttpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single(named(FACT_RF)) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .baseUrl(BuildConfig.CAT_FACT_API_ENDPOINT)
            .client(get(named(FACT_HTTPS)) as OkHttpClient)
            .build()
    }

    single {
        (get(named(FACT_RF)) as Retrofit).create(FactAPI::class.java)
    }
}