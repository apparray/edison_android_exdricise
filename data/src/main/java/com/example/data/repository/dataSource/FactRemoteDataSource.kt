package com.example.data.repository.dataSource

import com.example.domain.model.Fact
import retrofit2.Response

interface FactRemoteDataSource {
    suspend fun getDataSourceFacts(): Response<Fact>
}