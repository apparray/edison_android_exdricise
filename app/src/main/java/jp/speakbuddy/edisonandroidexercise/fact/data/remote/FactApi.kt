package jp.speakbuddy.edisonandroidexercise.fact.data.remote

import jp.speakbuddy.edisonandroidexercise.fact.data.remote.dto.FactInfoDto
import retrofit2.http.GET

interface FactApi {

    @GET("fact")
    suspend fun getFact(): FactInfoDto

    companion object {
        const val BASE_URL = "https://catfact.ninja/"
    }
}