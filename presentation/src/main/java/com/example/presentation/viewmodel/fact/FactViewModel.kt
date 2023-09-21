package com.example.presentation.viewmodel.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.mapper.getStringResId
import com.example.common.utils.wrapper.DataResult
import com.example.domain.usecase.fact.GetFactUseCase
import com.example.presentation.mapper.FactDomainPresentationMapper
import com.example.presentation.viewmodel.fact.FactContract.FactViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFactUseCase: GetFactUseCase,
    private val factDomainPresentationMapper: FactDomainPresentationMapper,
) : ViewModel() {

    private val _viewState = MutableStateFlow(FactViewState())

    val viewState: StateFlow<FactViewState> get() = _viewState

    /**
     * Handle events
     */
    fun setEvent(event: FactContract.FactEvent) {
        when (event) {
            is FactContract.FactEvent.RefreshData -> onFactObtained()
        }
    }

    /**
     * Set new ui state
     */
    private fun setState(reduce: FactViewState.() -> FactViewState) {
        val newState = viewState.value.reduce()
        _viewState.value = newState
    }

    private fun onFactObtained() {
        setState { copy(loading = true) }
        viewModelScope.launch {
            getFactUseCase.invoke().let { result ->
                if (result is DataResult.Success) {
                    val presentationModel = factDomainPresentationMapper.from(result.data)
                    setState {
                        copy(
                            loading = false,
                            retry = false,
                            fact = presentationModel.fact,
                            length = presentationModel.length.toString(),
                            showHintCats = presentationModel.showHintCats,
                            showHintLength = presentationModel.showHintLength
                        )
                    }
                } else if (result is DataResult.Error) {
                    setState {
                        copy(
                            loading = false,
                            retry = true,
                            errorMsg = result.exception.getStringResId(),
                            showHintCats = false,
                            showHintLength = false
                        )
                    }
                }
            }
        }
    }

}
