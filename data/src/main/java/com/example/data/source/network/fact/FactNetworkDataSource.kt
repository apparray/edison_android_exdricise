package com.example.data.source.network.fact

import com.example.common.utils.wrapper.DataResult
import com.example.data.model.FactDataModel

interface FactNetworkDataSource {
    suspend fun getFact(): DataResult<FactDataModel>
}
