package com.example.data.repository

import com.example.common.utils.wrapper.DataResult
import com.example.data.mapper.FactDomainMapper
import com.example.data.source.network.fact.FactNetworkDataSource
import com.example.domain.model.FactDomainModel
import com.example.domain.repository.fact.FactRepository
import javax.inject.Inject

class DefaultFactRepository @Inject constructor(
    private val factNetworkDataSource: FactNetworkDataSource,
    private val factDataDomainMapper: FactDomainMapper,
) : FactRepository {

    override suspend fun requestFact(): DataResult<FactDomainModel> {
        return when (val result = factNetworkDataSource.getFact()) {
            is DataResult.Success -> DataResult.Success(factDataDomainMapper.from(result.data))
            is DataResult.Error -> DataResult.Error(result.exception)
        }
    }
}
