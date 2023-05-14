package jp.speakbuddy.edisonandroidexercise

import android.app.Application
import jp.speakbuddy.edisonandroidexercise.di.dataStore
import jp.speakbuddy.edisonandroidexercise.di.dispatcherModule
import jp.speakbuddy.edisonandroidexercise.di.repositoryModule
import jp.speakbuddy.edisonandroidexercise.di.retrofitModule
import jp.speakbuddy.edisonandroidexercise.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EdisonAndroidExerciseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EdisonAndroidExerciseApplication)
            modules(
                retrofitModule,
                repositoryModule,
                viewModelModule,
                dataStore,
                dispatcherModule
            )
        }
    }
}
