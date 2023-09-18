package com.example.network.apiservice

import com.example.network.model.FactNetworkModel
import retrofit2.http.GET

interface FactApiService {
    @GET("fact")
    suspend fun getFact(): FactNetworkModel
}