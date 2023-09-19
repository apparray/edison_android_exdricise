package com.example.data.dummy.network

import com.example.common.utils.wrapper.DataResult
import com.example.data.model.FactDataModel
import com.example.data.source.network.fact.FactNetworkDataSource

class FakeBlogNetworkDataSource(
    private val getFact: DataResult<FactDataModel>,
) : FactNetworkDataSource {
    override suspend fun getFact(): DataResult<FactDataModel> {
        return getFact
    }
}
