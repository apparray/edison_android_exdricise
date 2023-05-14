package jp.speakbuddy.edisonandroidexercise.data.network.api

import jp.speakbuddy.edisonandroidexercise.data.network.view_data.Fact
import retrofit2.http.GET

interface FactAPI {
    @GET("fact")
    suspend fun getFact(): Fact
}