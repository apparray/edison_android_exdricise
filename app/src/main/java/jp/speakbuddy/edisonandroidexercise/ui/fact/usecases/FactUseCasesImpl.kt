package jp.speakbuddy.edisonandroidexercise.ui.fact.usecases

import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import javax.inject.Inject

class FactUseCasesImpl @Inject constructor(private val factRepository: FactRepository):
    FactUseCases {

    override suspend fun getFact(): Fact? {
       return factRepository.getFact()
    }
}