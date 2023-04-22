package jp.speakbuddy.edisonandroidexercise.repository.local

import jp.speakbuddy.edisonandroidexercise.repository.network.Fact

interface FactLocalDataStore {

    suspend fun addFact(fact: Fact)

    suspend fun getFact(): Fact?
}