package jp.speakbuddy.edisonandroidexercise.ui.repository.remote

import jp.speakbuddy.edisonandroidexercise.repository.network.FactService
import jp.speakbuddy.edisonandroidexercise.repository.remote.FactRemoteDataStore
import jp.speakbuddy.edisonandroidexercise.repository.remote.FactRemoteDataStoreImpl
import jp.speakbuddy.edisonandroidexercise.ui.TEST_FACT
import jp.speakbuddy.edisonandroidexercise.ui.TEST_FACT_RESPONSE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn

import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteDataStoreTest {

    private val factService: FactService = mock {
        onBlocking { it.getFact() } doReturn TEST_FACT_RESPONSE
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val remoteDataStore: FactRemoteDataStore =
        FactRemoteDataStoreImpl(factService, UnconfinedTestDispatcher())

    @Test
    fun `getFacts() should call FactSercie and return FactObject`() = runBlocking {
        val fact = remoteDataStore.getFact()

        Assert.assertEquals(TEST_FACT, fact)
    }

    @Test(expected = Exception::class)
    fun `getFacts() throws exception when provider fails`() = runBlocking {
        whenever(factService.getFact()).thenAnswer { Exception() }

        val fact = remoteDataStore.getFact()
    }
}

