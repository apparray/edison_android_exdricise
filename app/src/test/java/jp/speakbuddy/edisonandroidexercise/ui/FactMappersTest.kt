package jp.speakbuddy.edisonandroidexercise.ui

import jp.speakbuddy.edisonandroidexercise.data.toFact
import jp.speakbuddy.edisonandroidexercise.data.toFactState
import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.network.FactResponse
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FactMappersTest {

    @Test
    fun `test Fact mapping`() {
        val factResponse = FactResponse("some fact", 33)
        val fact = factResponse.toFact()
        assert(factResponse.fact == fact.text)
        assert(factResponse.length == fact.length)
    }

    @Test
    fun `test FactState mapping`() {
        val fact = Fact("some fact", 23)
        val factState = fact.toFactState()
        assert(fact.text == factState.fact)
        assert(fact.length == factState.length)
    }

    @Test
    fun `test isMultipleCats`() {
        val factWithCats = Fact("cats are cats")
        val factWithCats2 = Fact("Cats are dogs")
        val factWithCats3 = Fact("eating cats' food")
        val factWithCats4 = Fact("some CATS?")
        val factWithoutCats = Fact("cat is cat")
        val factWithCatsInAnotherWord = Fact("who is wearing a catsuit?")
        assertTrue(factWithCats.toFactState().isMultipleCats)
        assertTrue(factWithCats2.toFactState().isMultipleCats)
        assertTrue(factWithCats3.toFactState().isMultipleCats)
        assertTrue(factWithCats4.toFactState().isMultipleCats)
        assertFalse(factWithoutCats.toFactState().isMultipleCats)
        assertFalse(factWithCatsInAnotherWord.toFactState().isMultipleCats)
    }

    @Test
    fun `test isShowingLength`() {
        val lenLessThan100 = Fact("fact", 32)
        val len100 = Fact("fact", 100)
        val lenMoreThan100 = Fact("fact", 200)
        assertFalse(lenLessThan100.toFactState().isShowingLength)
        assertFalse(len100.toFactState().isShowingLength)
        assertTrue(lenMoreThan100.toFactState().isShowingLength)
    }

}