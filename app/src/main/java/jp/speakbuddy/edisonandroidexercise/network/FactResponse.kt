package jp.speakbuddy.edisonandroidexercise.network

import jp.speakbuddy.edisonandroidexercise.network.view_data.Fact

sealed class FactResponse {
    data class Success(val factResponse: Fact) : FactResponse()
    data class Error(val error: String) : FactResponse()
}