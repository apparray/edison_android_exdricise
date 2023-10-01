package jp.speakbuddy.edisonandroidexercise.fact.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.fact.domain.usecase.CheckFactContentUseCase
import jp.speakbuddy.edisonandroidexercise.fact.domain.usecase.CheckFactLengthUseCase
import jp.speakbuddy.edisonandroidexercise.fact.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.core.util.Resource
import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFact: GetFactUseCase,
    private val checkFactLength: CheckFactLengthUseCase,
    private val checkFactContent: CheckFactContentUseCase
) : ViewModel() {

    var state by mutableStateOf(FactUiState())

    init {
        updateFact { }
    }

    fun onEvent(event: FactUiEvent) {
        when(event) {
            is FactUiEvent.UpdateFact -> {
                updateFact { event.complete() }
            }
        }
    }

    private fun updateFact(completion: () -> Unit) {
        viewModelScope.launch {
            getFact().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            fact = result.data?.fact ?: "",
                            length = getFactLength(result.data),
                            showMultipleCats = checkFactContent(result.data, KEY_WORD),
                            isLoading = false,
                            error = null,
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            fact = result.data?.fact ?: "",
                            length = getFactLength(result.data),
                            showMultipleCats = checkFactContent(result.data, KEY_WORD),
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }.also { completion() }
            }
        }
    }

    private fun getFactLength(fact: FactInfo?, limit: Int = CHAR_LIMIT): String? {
        val exceedsLength = checkFactLength(fact, limit)
        return if (exceedsLength) fact?.length.toString() else null
    }

    companion object {
        private const val CHAR_LIMIT = 100
        private const val KEY_WORD = "cats"
    }
}
