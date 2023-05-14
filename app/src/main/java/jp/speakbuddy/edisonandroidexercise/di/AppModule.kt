package jp.speakbuddy.edisonandroidexercise.di

import com.google.gson.Gson
import jp.speakbuddy.edisonandroidexercise.BuildConfig
import jp.speakbuddy.edisonandroidexercise.Constants.Koin.FACT_HTTPS
import jp.speakbuddy.edisonandroidexercise.Constants.Koin.FACT_RF
import jp.speakbuddy.edisonandroidexercise.network.api.FactAPI
import jp.speakbuddy.edisonandroidexercise.network.interceptor.interceptor
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { FactViewModel(factRepository = get()) }
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