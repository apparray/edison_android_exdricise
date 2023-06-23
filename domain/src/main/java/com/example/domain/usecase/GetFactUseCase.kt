package com.example.domain.usecase

import com.example.domain.repository.FactRepository
import javax.inject.Inject

class GetFactUseCase @Inject constructor(private val factRepository: FactRepository) {
    suspend operator fun invoke() = factRepository.getFacts()
}