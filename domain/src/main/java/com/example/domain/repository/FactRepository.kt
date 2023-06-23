package com.example.domain.repository

import com.example.domain.model.Fact
import com.example.domain.util.Resource

interface FactRepository {
    suspend fun getFacts(): Resource<Fact>
}