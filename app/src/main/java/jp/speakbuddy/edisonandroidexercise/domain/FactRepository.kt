package jp.speakbuddy.edisonandroidexercise.domain

import jp.speakbuddy.edisonandroidexercise.model.Fact

interface FactRepository {
    suspend fun getFact(): Fact
}