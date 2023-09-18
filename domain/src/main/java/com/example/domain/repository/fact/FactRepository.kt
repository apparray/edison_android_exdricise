package com.example.domain.repository.fact

import com.example.common.utils.wrapper.DataResult
import com.example.domain.model.FactDomainModel

interface FactRepository {
    suspend fun requestFact(): DataResult<FactDomainModel>
}
