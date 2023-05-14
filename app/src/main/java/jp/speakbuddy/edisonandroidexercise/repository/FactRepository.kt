package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.data.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.network.api.FactAPI

class FactRepository(private val factAPI: FactAPI) {

    suspend fun getFact(): FactResponse {
        return try {
            val response = factAPI.getFact()
            FactResponse.Success(response)
        } catch (e: Exception) {
            FactResponse.Error(e.localizedMessage ?: "")
        }
    }
}