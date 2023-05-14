package jp.speakbuddy.edisonandroidexercise.network.api

import jp.speakbuddy.edisonandroidexercise.network.view_data.Fact
import retrofit2.http.GET

interface FactAPI {
    @GET("fact")
    suspend fun getFact(): Fact
}