package com.example.network.source

import android.net.Uri
import com.example.common.exception.NetworkException
import com.example.common.mapper.ExceptionMapper
import com.example.common.utils.wrapper.DataResult
import com.example.data.model.FactDataModel
import com.example.network.apiservice.FactApiService
import com.example.network.fakes.FakeConnectivityCheckReturnError
import com.example.network.fakes.FakeConnectivityCheckReturnSuccess
import com.example.network.mapper.FactNetworkDataMapper
import com.example.network.utils.Extensions.moshi
import com.example.network.utils.enqueueResponse
import io.mockk.MockKAnnotations
import io.mockk.mockkStatic
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DefaultFactNetworkDataSourceTest {
    // systemUnderTest
    private lateinit var sut: DefaultFactNetworkDataSource

    private val mockWebServer = MockWebServer()

    private lateinit var factApiService: FactApiService
    private lateinit var exceptionMapper: ExceptionMapper
    private lateinit var factNetworkDataMapper: FactNetworkDataMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic(Uri::class)

        factApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FactApiService::class.java)

        factNetworkDataMapper = FactNetworkDataMapper()

        exceptionMapper = ExceptionMapper()

        sut = DefaultFactNetworkDataSource(
            factApiService = factApiService,
            factNetworkDataMapper = factNetworkDataMapper,
            connectivityChecker = FakeConnectivityCheckReturnSuccess(),
            exceptionMapper = exceptionMapper
        )
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getFact() returns fact when status is 200`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse(fileName = "fact.json") { body ->
            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }
        val expected = FactDataModel(
            fact = "Kittens remain with their mother till the age of 9 weeks.",
            length = 57
        )

        // When
        val response = sut.getFact()

        // Then
        val actual = (response as DataResult.Success).data

        // Assertion
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun `getBlogs() throws NetworkUnavailable exception when network is offline`() =
        runBlocking {
            // Given
            sut = DefaultFactNetworkDataSource(
                factApiService = factApiService,
                factNetworkDataMapper = factNetworkDataMapper,
                connectivityChecker = FakeConnectivityCheckReturnError(),
                exceptionMapper = exceptionMapper
            )

            val expected = NetworkException.NetworkUnavailable

            // When
            val response = sut.getFact()

            // Then
            val actual = (response as DataResult.Error).exception

            // Assertion
            TestCase.assertEquals(expected, actual)
        }
    @Test
    fun `getFact() throws Network exception when server is unreachable`() = runBlocking {
        // Given
        val expected = NetworkException.Network

        // When
        val response = sut.getFact()

        // Then
        val actual = (response as DataResult.Error).exception

        // Assertion
        TestCase.assertEquals(expected, actual)
    }

}