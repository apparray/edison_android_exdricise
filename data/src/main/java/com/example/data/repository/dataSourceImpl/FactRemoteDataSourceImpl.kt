package com.example.data.repository.dataSourceImpl

import com.example.data.api.FactApi
import com.example.data.repository.dataSource.FactRemoteDataSource

class FactRemoteDataSourceImpl(private val factApi: FactApi): FactRemoteDataSource {
    override suspend fun getDataSourceFacts() = factApi.getFact()
}