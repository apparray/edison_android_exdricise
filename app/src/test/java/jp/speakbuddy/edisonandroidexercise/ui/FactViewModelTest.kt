package jp.speakbuddy.edisonandroidexercise.ui

import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import org.junit.Test

class FactViewModelTest {

    private val viewModel = FactViewModel(GetFactStateUseCaseMock())

    @Test
    fun updateFact() {
        val initialFact = viewModel.factState.value.fact
        viewModel.updateFact()
        val fact: String = viewModel.factState.value.fact
        assert(fact != initialFact)
    }
}
