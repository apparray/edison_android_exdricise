package jp.speakbuddy.edisonandroidexercise.ui.fact.usecases

import jp.speakbuddy.edisonandroidexercise.repository.network.Fact

interface FactUseCases {

    suspend fun getFact(): Fact?
}