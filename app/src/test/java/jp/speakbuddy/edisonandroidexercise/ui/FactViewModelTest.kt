package jp.speakbuddy.edisonandroidexercise.ui

import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.ui.fact.usecases.FactUseCases
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class FactViewModelTest {
    private val useCases: FactUseCases = mock{

    }

    private val viewModel = FactViewModel(useCases)

    @Test
    fun `updateFact() should return null when getFact throws an exception`() = runBlockingTest {
        // Given
        `when`(useCases.getFact()).thenThrow(RuntimeException())

        // When
        var completionCalled = false
        val result = viewModel.updateFact { completionCalled = true }

        // Then
        assertNull(result)
        assertTrue(completionCalled)
    }

    @Test
    fun `updateFact() should return a fact when getFact succeeds`() = runBlockingTest {
        // Given
        val fact = TEST_FACT
        `when`(useCases.getFact()).thenReturn(fact)

        // When
        var completionCalled = false
        val result = viewModel.updateFact { completionCalled = true }

        // Then
        assertEquals(fact, result)
        assertTrue(completionCalled)
    }
}
