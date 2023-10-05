package jp.speakbuddy.edisonandroidexercise.domain

import jp.speakbuddy.edisonandroidexercise.data.toFactState
import jp.speakbuddy.edisonandroidexercise.model.FactState
import javax.inject.Inject

class GetFactStateUseCaseImpl @Inject constructor(private val repository: FactRepository) : GetFactStateUseCase {

    override suspend fun getFactState(): FactState {
        return repository.getFact().toFactState()
    }

}