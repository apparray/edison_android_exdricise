package com.example.data.repository

import com.example.common.exception.NetworkException
import com.example.common.utils.wrapper.DataResult
import com.example.data.dummy.FakeDataGenerator
import com.example.data.dummy.network.FakeBlogNetworkDataSource
import com.example.data.mapper.FactDomainMapper
import com.example.domain.repository.fact.FactRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FactRepositoryTest {

    private lateinit var sut: FactRepository
    private lateinit var factDataDomainMapper: FactDomainMapper

    @Before
    fun setup() {
        factDataDomainMapper = FactDomainMapper()

        // systemUnderTest
        sut = DefaultFactRepository(
            factNetworkDataSource = FakeBlogNetworkDataSource(
                getFact = DataResult.Success(
                    FakeDataGenerator.fact
                )
            ),
            factDataDomainMapper = factDataDomainMapper
        )
    }

    @Test
    fun `getFact() returns Fact when NetworkDataSource returns success`() = runBlocking {
        // Given
        val expected = factDataDomainMapper.from(FakeDataGenerator.fact)

        // When
        val result = sut.requestFact()

        // Then
        val actual = (result as DataResult.Success).data

        // Assertion
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun `getFact() returns error when NetworkDataSource throws NetworkUnavailable exception`() =
        runBlocking {
            // Given
            sut = DefaultFactRepository(
                factNetworkDataSource = FakeBlogNetworkDataSource(
                    getFact = DataResult.Error(
                        NetworkException.NetworkUnavailable
                    )
                ),
                factDataDomainMapper = factDataDomainMapper
            )
            val expected = NetworkException.NetworkUnavailable

            // When
            val result = sut.requestFact()

            // Then
            val actual = (result as DataResult.Error).exception

            // Assertion
            TestCase.assertEquals(expected, actual)
        }

    @Test
    fun `getFact() returns error when NetworkDataSource throws Network exception`() =
        runBlocking {
            // Given
            sut = DefaultFactRepository(
                factNetworkDataSource = FakeBlogNetworkDataSource(
                    getFact = DataResult.Error(
                        NetworkException.Network
                    )
                ),
                factDataDomainMapper = factDataDomainMapper
            )

            val expected = NetworkException.Network

            // When
            val result = sut.requestFact()

            // Then
            val actual = (result as DataResult.Error).exception

            // Assertion
            TestCase.assertEquals(expected, actual)
        }
}