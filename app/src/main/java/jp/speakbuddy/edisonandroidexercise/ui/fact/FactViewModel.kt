package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.ui.fact.usecases.FactUseCases
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(private val factUseCases: FactUseCases) : ViewModel() {

    fun updateFact(completion: () -> Unit): Fact? =
        runBlocking {
            try {
                factUseCases.getFact()
            } catch (e: Throwable) {
                null
                // "something went wrong. error = ${e.message}"
            }.also { completion() }
        }
}
