package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.repository.local.FactLocalDataStore
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.repository.remote.FactRemoteDataStore
import javax.inject.Inject
import kotlin.random.Random

class FactRepositoryImpl @Inject constructor(
    private val remoteDataStore: FactRemoteDataStore,
    private val localDataStore: FactLocalDataStore,
    private val random: RandomManager // added here only for testing
) : FactRepository {

    // I was thinking of an ACTUAL use case for local data in such api(since it only gives new random facts)
    // The best idea I came up with is that we build a local history of facts and then there will be
    // a 50% chance to see to see random locally stored fact(at least it makes sense and we reduce the
    // network data usage a bit + it demonstrates how to implement local storage in the code)
    override suspend fun getFact(): Fact? {
        val getLocalFact = random.nextBoolean()
        val localFact = localDataStore.getFact()
        return if (getLocalFact && localFact != null) {
           localFact
        } else {
            val fact = remoteDataStore.getFact()
            localDataStore.addFact(fact)
            return fact
        }
    }
}

// purely for randomization and testing
interface RandomManager {
    fun nextBoolean(): Boolean
}

class RandomManagerImpl: RandomManager {
    override fun nextBoolean(): Boolean {
        return Random.nextBoolean()
    }
}