package com.example.data.api

import com.example.domain.model.Fact
import retrofit2.Response
import retrofit2.http.GET

interface FactApi {
    @GET("fact")
    suspend fun getFact(): Response<Fact>
}