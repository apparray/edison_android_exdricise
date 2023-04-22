package jp.speakbuddy.edisonandroidexercise.ui.repository

import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.FactRepositoryImpl
import jp.speakbuddy.edisonandroidexercise.repository.RandomManager
import jp.speakbuddy.edisonandroidexercise.repository.local.FactLocalDataStore
import jp.speakbuddy.edisonandroidexercise.repository.remote.FactRemoteDataStore
import jp.speakbuddy.edisonandroidexercise.ui.TEST_FACT
import jp.speakbuddy.edisonandroidexercise.ui.TEST_LOCAL_STORED_FACT
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FactRepositoryTest {

    private var localDataStore: FactLocalDataStore = mock {
        onBlocking { getFact() } doReturn TEST_LOCAL_STORED_FACT
    }

    private var remoteDataStore: FactRemoteDataStore = mock {
        onBlocking { getFact() } doReturn TEST_FACT
    }

    private var random: RandomManager = mock {
        onBlocking { nextBoolean() } doReturn true
    }

    private val repository: FactRepository =
        FactRepositoryImpl(remoteDataStore, localDataStore, random)

    @Test
    fun `getFact() should return locally stored Fact object if theres is some stored and we are getting local Fact`() = runBlocking {
        val fact = repository.getFact()

        Assert.assertEquals(fact, TEST_LOCAL_STORED_FACT)
    }

    @Test
    fun `getFact() should get remote Fact if we are not getting local Fact and save it locally`() = runBlocking {
        whenever(random.nextBoolean()).thenReturn(false)

        val fact = repository.getFact()

        verify(localDataStore).addFact(TEST_FACT)
        Assert.assertEquals(fact, TEST_FACT)
    }

    @Test
    fun `getFact() should get remote Fact if we don't have locally saved Fact even if we are getting local fact and save it locally`() = runBlocking {
        whenever(random.nextBoolean()).thenReturn(true)
        whenever(localDataStore.getFact()).thenReturn(null)

        val fact = repository.getFact()

        verify(localDataStore).addFact(TEST_FACT)
        Assert.assertEquals(fact, TEST_FACT)
    }

    @Test(expected = Exception::class)
    fun `getFact() throws exception when provider fails`() = runBlocking {
        whenever(random.nextBoolean()).thenReturn(false)
        whenever(remoteDataStore.getFact()).thenAnswer { Exception() }

        val fact = repository.getFact()
    }
}
