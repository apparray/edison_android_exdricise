package jp.speakbuddy.edisonandroidexercise.fact.data.repository

import jp.speakbuddy.edisonandroidexercise.fact.data.local.FactDataStore
import jp.speakbuddy.edisonandroidexercise.fact.data.remote.FactApi
import jp.speakbuddy.edisonandroidexercise.fact.data.remote.dto.toFactInfo
import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import jp.speakbuddy.edisonandroidexercise.fact.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val api: FactApi,
    private val datastore: FactDataStore
): FactRepository {

    override fun getFact(): Flow<Resource<FactInfo>> = flow {
        emit(Resource.Loading())

        val fact = datastore.getFact()
        emit(Resource.Loading(data = fact))

        try {
            val remoteFact = api.getFact()
            datastore.setFact(remoteFact.toFactInfo())
        } catch(e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = fact
            ))
        } catch(e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = fact
            ))
        }

        val newFact = datastore.getFact()
        emit(Resource.Success(data = newFact))
    }
}