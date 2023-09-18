package com.example.network.source

import com.example.common.exception.NetworkException
import com.example.common.mapper.ExceptionMapper
import com.example.common.utils.wrapper.DataResult
import com.example.data.model.FactDataModel
import com.example.data.connectivity.ConnectivityChecker
import com.example.data.source.network.fact.FactNetworkDataSource
import com.example.network.apiservice.FactApiService
import com.example.network.mapper.FactNetworkDataMapper
import javax.inject.Inject

class DefaultFactNetworkDataSource @Inject constructor(
    private val factApiService: FactApiService,
    private val factNetworkDataMapper: FactNetworkDataMapper,
    private val connectivityChecker: ConnectivityChecker,
    private val exceptionMapper: ExceptionMapper
) : FactNetworkDataSource {

    override suspend fun getFact(): DataResult<FactDataModel> {
        if (!connectivityChecker.hasInternetAccess()) {
            return DataResult.Error(NetworkException.NetworkUnavailable)
        }
        return try {
            val fact = factApiService.getFact()
            DataResult.Success(factNetworkDataMapper.from(fact))
        } catch (exception: Exception) {
            DataResult.Error(exceptionMapper.mapError(exception))
        }
    }
}
