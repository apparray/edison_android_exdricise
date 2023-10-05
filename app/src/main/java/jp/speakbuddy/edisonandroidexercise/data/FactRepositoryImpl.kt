package jp.speakbuddy.edisonandroidexercise.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.speakbuddy.edisonandroidexercise.domain.FactRepository
import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@Suppress("PrivatePropertyName")
class FactRepositoryImpl @Inject constructor(
    private val service: FactService,
    private val dataStore: DataStore<Preferences>
) : FactRepository {

    private val FACT_TEXT_KEY = stringPreferencesKey("FACT_TEXT_KEY")
    private val FACT_LENGTH_KEY = intPreferencesKey("FACT_LENGTH_KEY")

    private var firstLaunch = true

    override suspend fun getFact(): Fact {
        return try {
            if (firstLaunch) {
                firstLaunch = false
                getLatestFact()
            } else {
                service.getFact().toFact().also { saveFact(it) }
            }
        } catch (e: Throwable) {
            Fact("something went wrong. error = ${e.message}", 0)
        }
    }

    private suspend fun getLatestFact(): Fact {
        val prefs = dataStore.data.first()
        val factText = prefs[FACT_TEXT_KEY] ?: ""
        val factLength = prefs[FACT_LENGTH_KEY] ?: 0
        return Fact(factText, factLength)
    }

    private suspend fun saveFact(fact: Fact) {
        dataStore.edit {
            it[FACT_TEXT_KEY] = fact.text
            it[FACT_LENGTH_KEY] = fact.length
        }
    }
}



