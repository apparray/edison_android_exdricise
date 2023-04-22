package jp.speakbuddy.edisonandroidexercise.ui.usecases

import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.ui.TEST_FACT
import jp.speakbuddy.edisonandroidexercise.ui.fact.usecases.FactUseCasesImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FactUseCasesTest {

    private val repository: FactRepository = mock {
        onBlocking { getFact() } doReturn TEST_FACT
    }

    private val useCases = FactUseCasesImpl(repository)

    @Test
    fun `getFact() should call repository and return Fact data`() = runBlocking {
        val fact = useCases.getFact()

        Assert.assertEquals(fact, TEST_FACT)
    }

    @Test(expected = Exception::class)
    fun `getFact() should return error in case of some error`() = runBlocking {
        whenever(useCases.getFact()).thenAnswer { Exception() }

        val fact = useCases.getFact()
    }
}