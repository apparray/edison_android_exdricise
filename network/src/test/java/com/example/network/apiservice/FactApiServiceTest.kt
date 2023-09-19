package com.example.network.apiservice

import com.example.network.model.FactNetworkModel
import com.example.network.utils.Extensions
import com.example.network.utils.enqueueResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class FactApiServiceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(Extensions.moshi))
        .build().create(FactApiService::class.java)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Given 200 response When fetching fact Then returns currencies correctly`() {
        // Given
        mockWebServer.enqueueResponse(fileName = "fact.json") { body ->
            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        val expected = FactNetworkModel(
            fact = "Kittens remain with their mother till the age of 9 weeks.",
            length = 57
        )

        // When
        val actual = runBlocking { api.getFact() }
        val request = mockWebServer.takeRequest()


        // Then
        assertEquals(expected, actual)
        assertEquals("/fact", request.path)
    }

    @Test(expected = HttpException::class)
    fun `It'll change into HttpException when response code is not 2XX`() {
        // Given
        mockWebServer.enqueueResponse(fileName = "fact.json") { body ->
            MockResponse()
                .setResponseCode(300)
                .setBody(body)
        }

        runBlocking { api.getFact() }
    }

}