package jp.speakbuddy.edisonandroidexercise.fact.domain.usecase

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import jp.speakbuddy.edisonandroidexercise.fact.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFactUseCase @Inject constructor(
    private val repository: FactRepository
) {

    operator fun invoke(): Flow<Resource<FactInfo>> {
        return repository.getFact()
    }
}