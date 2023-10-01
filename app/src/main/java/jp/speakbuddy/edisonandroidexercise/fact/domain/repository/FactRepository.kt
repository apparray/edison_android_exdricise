package jp.speakbuddy.edisonandroidexercise.fact.domain.repository

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import jp.speakbuddy.edisonandroidexercise.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface FactRepository {

    fun getFact(): Flow<Resource<FactInfo>>
}