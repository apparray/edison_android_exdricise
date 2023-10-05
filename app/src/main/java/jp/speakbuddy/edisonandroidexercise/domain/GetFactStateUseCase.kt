package jp.speakbuddy.edisonandroidexercise.domain

import jp.speakbuddy.edisonandroidexercise.model.FactState

interface GetFactStateUseCase {
    suspend fun getFactState(): FactState
}