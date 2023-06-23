package jp.speakbuddy.edisonandroidexercise.ui

import com.example.domain.model.Fact
import com.example.domain.repository.FactRepository
import com.example.domain.usecase.GetFactUseCase
import com.example.domain.util.Resource
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FactViewModelTest {

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var factViewModel: FactViewModel

    private lateinit var getFactUseCase: GetFactUseCase

    @Mock
    lateinit var factRepository: FactRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        getFactUseCase = GetFactUseCase(factRepository)
        factViewModel = FactViewModel(getFactUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `testUpdateFact Success`(): Unit = runBlocking {
        // Create a mock instance of GetFactUseCase
        val getFactUseCase = mock(GetFactUseCase::class.java)
        val fact = Fact("Sample fact", 100)
        `when`(getFactUseCase()).thenReturn(Resource.Success(fact))

        // Create an instance of FactViewModel using the mock GetFactUseCase
        val viewModel = FactViewModel(getFactUseCase)

        // Define a completion callback and verify its invocation
        var completionCalled = false
        val completion = {
            completionCalled = true
        }

        // Call the updateFact method
        val result = viewModel.updateFact(completion)

        // Verify that the completion callback was called
        assertTrue(completionCalled)

        // Verify that the result is not null and matches the expected fact
        assertNotNull(result)
        assertEquals(fact, result)

        // Verify that the getFactUseCase was called
        verify(getFactUseCase, times(1))()
    }

    @org.junit.Test
    fun `testUpdateFact_Error`(): Unit = runBlocking {
        // Create a mock instance of GetFactUseCase
        val getFactUseCase = mock(GetFactUseCase::class.java)
        val error = "NullPointer Exception"
        `when`(getFactUseCase()).thenReturn(Resource.Error(error))

        // Create an instance of FactViewModel using the mock GetFactUseCase
        val viewModel = FactViewModel(getFactUseCase)

        // Define a completion callback and verify its invocation
        var completionCalled = false
        val completion = {
            completionCalled = true
        }

        // Call the updateFact method
        val result = viewModel.updateFact(completion)

        // Verify that the completion callback was called
        assertTrue(completionCalled)

        // Verify that the result is null
        assertNull(result)

        // Verify that the getFactUseCase was called
        verify(getFactUseCase, times(1))()

        // Verify that the error message was logged
        // Here, you can use any logging assertion library or verify the behavior of your logging mechanism
        // For example, if you are using `android.util.Log`, you can use `verify` from Mockito to check if the `e.printStackTrace()` is called
        // verify(mockLogger).e("exception", error)

        println("Error message: $error")
    }
}