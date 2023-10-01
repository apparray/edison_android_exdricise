package jp.speakbuddy.edisonandroidexercise.fact.domain.usecase

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CheckFactLengthUseCaseTest {

    private val sut = CheckFactLengthUseCase()

    @Test
    fun `when fact length is greater than limit a value of true is returned`() {
        val limit = 100
        val factInfo = FactInfo("Cat families usually play best in even numbers. cats and kittens should be acquired in pairs whenever possible", 111)

        val result = sut(factInfo, limit)
        assertTrue(result)
    }

    @Test
    fun `when fact length is less than limit a value of false is returned`() {
        val limit = 100
        val factInfo = FactInfo("Cats are cute", 13)

        val result = sut(factInfo, limit)
        assertFalse(result)
    }

    @Test
    fun`when fact is null a value of false is returned`() {
        val limit = 100
        val factInfo = null

        val result = sut(factInfo, limit)
        assertFalse(result)
    }
}