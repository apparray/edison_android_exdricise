package jp.speakbuddy.edisonandroidexercise.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.speakbuddy.edisonandroidexercise.data.FactRepositoryImpl
import jp.speakbuddy.edisonandroidexercise.domain.FactRepository
import jp.speakbuddy.edisonandroidexercise.domain.GetFactStateUseCase
import jp.speakbuddy.edisonandroidexercise.domain.GetFactStateUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class FactModule {

    @Binds
    abstract fun bindFactRepository(impl: FactRepositoryImpl): FactRepository

    @Binds
    abstract fun bindGetFactStateUseCase(impl: GetFactStateUseCaseImpl): GetFactStateUseCase

}