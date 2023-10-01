package jp.speakbuddy.edisonandroidexercise.fact.domain.usecase

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CheckFactContentUseCaseTest {

    private val sut = CheckFactContentUseCase()

    @Test
    fun `when fact contains keyword a value of true is returned`() {
        val keyword = "cats"
        val factInfo = FactInfo("cats are cute", 13)

        val result = sut(factInfo, keyword)
        assertTrue(result)
    }

    @Test
    fun`when fact does not contain keyword a value of false is returned`() {
        val keyword = "cats"
        val factInfo = FactInfo("A cat is not a dog", 18)

        val result = sut(factInfo, keyword)
        assertFalse(result)
    }

    @Test
    fun`when fact is null a value of false is returned`() {
        val keyword = "cats"
        val factInfo = null

        val result = sut(factInfo, keyword)
        assertFalse(result)
    }
}