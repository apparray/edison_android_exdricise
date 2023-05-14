package jp.speakbuddy.edisonandroidexercise.ui.viewmodel_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.data.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.ui.Mock.catsFactMock
import jp.speakbuddy.edisonandroidexercise.ui.Mock.longLength
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.ui.factObjectMock
import jp.speakbuddy.edisonandroidexercise.ui.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class FactViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FactViewModel
    private val repository = mockk<FactRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        coEvery { repository.getFact() } returns FactResponse.Success(
            factObjectMock
        )

        viewModel = FactViewModel(repository, dispatcher, storage)
    }

    @Test
    fun callUpdateFactFunction_getFactData() = runTest {
        viewModel.updateFact()
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.fact.value == catsFactMock)
        assert(viewModel.length.value == longLength)
    }

    @Test
    fun callUpdateFactFunction_getLocalStoredFactWhenErrorData() = runTest {
        coEvery { repository.getFact() } returns FactResponse.Error(
            error = "Error message"
        )
        viewModel.updateFact()
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.fact.value == catsFactMock)
        assert(viewModel.length.value == longLength)
    }
}