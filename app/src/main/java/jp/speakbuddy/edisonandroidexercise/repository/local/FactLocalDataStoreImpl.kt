package jp.speakbuddy.edisonandroidexercise.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.repository.network.Facts
import jp.speakbuddy.edisonandroidexercise.repository.network.FactsSerializer

import kotlinx.coroutines.flow.firstOrNull



//val Context.dataStore: DataStore<Facts> by dataStore(fileName = PREFERENCE_NAME, FactsSerializer)

class FactLocalDataStoreImpl(
    private val dataStore: DataStore<Facts>
) : FactLocalDataStore {

    override suspend fun addFact(fact: Fact) {
        dataStore.updateData { currentFacts ->
            currentFacts.copy(data = currentFacts.data + fact)
        }
    }

    override suspend fun getFact(): Fact? {
        val factList = dataStore.data.firstOrNull()?.data ?: emptyList()
        return selectRandomFact(factList)
    }

    private fun selectRandomFact(data: List<Fact>): Fact? {
        return data.shuffled().firstOrNull()
    }

}