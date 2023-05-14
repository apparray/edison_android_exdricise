package jp.speakbuddy.edisonandroidexercise.data.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig

val interceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
}