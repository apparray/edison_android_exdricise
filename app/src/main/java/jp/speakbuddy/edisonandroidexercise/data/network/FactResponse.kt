package jp.speakbuddy.edisonandroidexercise.data.network

import jp.speakbuddy.edisonandroidexercise.data.network.view_data.Fact

sealed class FactResponse {
    data class Success(val factResponse: Fact) : FactResponse()
    data class Error(val error: String) : FactResponse()
}