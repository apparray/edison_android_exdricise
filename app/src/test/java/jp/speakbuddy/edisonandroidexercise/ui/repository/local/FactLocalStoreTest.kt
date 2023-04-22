package jp.speakbuddy.edisonandroidexercise.ui.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import jp.speakbuddy.edisonandroidexercise.repository.local.FactLocalDataStore
import jp.speakbuddy.edisonandroidexercise.repository.local.FactLocalDataStoreImpl
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.repository.network.Facts
import jp.speakbuddy.edisonandroidexercise.repository.network.FactsSerializer
import jp.speakbuddy.edisonandroidexercise.ui.TEST_FACT
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

private const val PREFERENCE_NAME = "facts"

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class FactLocalStoreTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val dataStore: DataStore<Facts> = DataStoreFactory.create(
        serializer = FactsSerializer,
        produceFile = { context.preferencesDataStoreFile(PREFERENCE_NAME) }

    )

    private val localDataStore: FactLocalDataStore = FactLocalDataStoreImpl(dataStore)

    @Test
    fun `addFact should update data store with new fact`(): Unit = runBlocking {
        localDataStore.addFact(TEST_FACT)

        val stored = localDataStore.getFact()
        Assert.assertEquals(stored, TEST_FACT)
    }

    @Test
    fun `getFact should return null when data store is empty`() = runBlocking {

        val fact = localDataStore.getFact()

        assertNull(fact)
    }

    @Test
    fun `getFact should return random fact when data store is not empty`() = runBlocking {
        val fact1 = Fact("Fact 1", 6)
        val fact2 = Fact("Fact 2", 7)
        val fact3 = Fact("Fact 3", 8)
        val factList = listOf(fact1, fact2, fact3)
        localDataStore.addFact(fact1)
        localDataStore.addFact(fact2)
        localDataStore.addFact(fact3)

        val fact = localDataStore.getFact()

        assertNotNull(fact)
        assertTrue(factList.contains(fact))
    }
}