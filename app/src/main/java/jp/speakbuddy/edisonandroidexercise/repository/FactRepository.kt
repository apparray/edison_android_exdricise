package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.repository.network.Fact

interface FactRepository {

    suspend fun getFact(): Fact?
}