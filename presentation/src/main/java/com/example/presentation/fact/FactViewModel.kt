package com.example.presentation.fact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.wrapper.DataResult
import com.example.domain.usecase.fact.GetFactUseCase
import com.example.presentation.fact.FactContract.FactViewEffect
import com.example.presentation.fact.FactContract.FactViewState
import com.example.presentation.mapper.FactDomainPresentationMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFactUseCase: GetFactUseCase,
    private val factDomainPresentationMapper: FactDomainPresentationMapper,
) : ViewModel() {
    private val currentState: FactViewState
        get() = viewState.value

    private val _viewState = MutableStateFlow(FactViewState())

    val viewState: StateFlow<FactViewState> get() = _viewState

    private val _viewEffect: Channel<FactViewEffect> = Channel()
    val viewEffect = _viewEffect.receiveAsFlow()

    /**
     * Handle events
     */
    fun setEvent(event: FactContract.FactEvent) {
        when (event) {
            is FactContract.FactEvent.Initialization -> onInitialization()
            is FactContract.FactEvent.RefreshData -> onFactObtained()
            else -> {}
        }
    }

    /**
     * Set new effect
     */
    private fun setEffect(builder: () -> FactViewEffect) {
        val effectValue = builder()
        setState { copy(loading = false) }
        viewModelScope.launch { _viewEffect.send(effectValue) }
    }

    /**
     * Set new ui state
     */
    private fun setState(reduce: FactViewState.() -> FactViewState) {
        val newState = viewState.value.reduce()
        _viewState.value = newState
    }

    private fun onInitialization() {
        setState { copy(loading = false) }
    }

    private fun onFactObtained() {
        viewModelScope.launch {
            getFactUseCase.invoke().let { result ->
                if (result is DataResult.Success) {
                    val presentationModel = factDomainPresentationMapper.from(result.data)
                    setState { copy(loading = false) }
                } else if (result is DataResult.Error) {
                    setState { copy(loading = false) }
                }
            }
        }
    }

}
