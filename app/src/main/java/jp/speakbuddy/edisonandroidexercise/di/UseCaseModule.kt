package jp.speakbuddy.edisonandroidexercise.di

import com.example.domain.repository.FactRepository
import com.example.domain.usecase.GetFactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetFactsUseCase(factRepository: FactRepository) =
       GetFactUseCase(factRepository)
}