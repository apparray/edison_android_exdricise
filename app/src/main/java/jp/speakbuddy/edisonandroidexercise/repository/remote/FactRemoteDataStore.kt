package jp.speakbuddy.edisonandroidexercise.repository.remote

import jp.speakbuddy.edisonandroidexercise.repository.network.Fact

interface FactRemoteDataStore {
    suspend fun getFact(): Fact
}