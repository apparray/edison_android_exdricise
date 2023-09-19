package com.example.domain.usecase.fact

import com.example.common.qualifier.IoDispatcher
import com.example.common.utils.wrapper.DataResult
import com.example.domain.model.FactDomainModel
import com.example.domain.repository.fact.FactRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFactUseCase @Inject constructor(
    private val factRepository: FactRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): DataResult<FactDomainModel> {
        return when (
            val result = withContext(dispatcher) {
                factRepository.requestFact()
            }) {
            is DataResult.Success -> DataResult.Success(result.data)
            is DataResult.Error -> DataResult.Error(result.exception)
        }
    }
}