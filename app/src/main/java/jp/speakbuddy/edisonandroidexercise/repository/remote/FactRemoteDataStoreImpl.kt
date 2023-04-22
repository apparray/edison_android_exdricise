package jp.speakbuddy.edisonandroidexercise.repository.remote

import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.repository.network.FactService
import jp.speakbuddy.edisonandroidexercise.repository.network.toFact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FactRemoteDataStoreImpl @Inject constructor(
    private val provider: FactService,
    private val ioDispatcher: CoroutineDispatcher
) : FactRemoteDataStore {

    override suspend fun getFact(): Fact =
        withContext(ioDispatcher) {
            provider.getFact().toFact()
        }
}