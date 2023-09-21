package com.example.presentation.viewmodel.fact

import app.cash.turbine.test
import com.example.common.exception.NetworkException
import com.example.common.mapper.getStringResId
import com.example.common.utils.wrapper.DataResult
import com.example.domain.model.FactDomainModel
import com.example.domain.usecase.fact.GetFactUseCase
import com.example.presentation.TestCoroutineRule
import com.example.presentation.mapper.FactDomainPresentationMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FactViewModelTest {

    // systemUnderTest
    private lateinit var sut: FactViewModel

    @MockK
    private lateinit var getBlogUseCase: GetFactUseCase

    private lateinit var factDomainPresentationMapper: FactDomainPresentationMapper

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        // Turn relaxUnitFun on for all mocks
        MockKAnnotations.init(this, relaxUnitFun = true)

        factDomainPresentationMapper = FactDomainPresentationMapper()

        sut = FactViewModel(
            getFactUseCase = getBlogUseCase,
            factDomainPresentationMapper = factDomainPresentationMapper,
        )
    }

    @Test
    fun `when GetFactUseCase returns success then verify viewState`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val getFactResult = DataResult.Success(FactDomainModel("test", 4))

            coEvery { getBlogUseCase.invoke() } returns getFactResult

            val expectedViewState = FactContract.FactViewState(
                loading = false,
                retry = false,
                fact = getFactResult.data.fact,
                length = getFactResult.data.length.toString(),
                showHintCats = false,
                showHintLength = false
            )

            // When
            sut.setEvent(FactContract.FactEvent.RefreshData)

            // Then
            sut.viewState.test {
                val actual = awaitItem()

                // Assertion
                Assert.assertEquals(actual, expectedViewState)
                expectNoEvents()
            }
        }


    @Test
    fun `when GetFactUseCase returns NetworkUnavailable then verify viewState`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { getBlogUseCase.invoke() } returns DataResult.Error(NetworkException.NetworkUnavailable)
            val expectedViewState = FactContract.FactViewState(
                loading = false,
                retry = true,
                errorMsg = NetworkException.NetworkUnavailable.getStringResId(),
                showHintCats = false,
                showHintLength = false
            )

            // When
            sut.setEvent(FactContract.FactEvent.RefreshData)

            // Then
            sut.viewState.test {
                val actual = awaitItem()

                // Assertion
                Assert.assertEquals(actual, expectedViewState)
                expectNoEvents()
            }
        }

}