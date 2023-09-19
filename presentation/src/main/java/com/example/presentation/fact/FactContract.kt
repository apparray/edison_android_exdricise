package com.example.presentation.fact

class FactContract {
    sealed class FactEvent {
        object Initialization : FactEvent()
        object RefreshData : FactEvent()
    }

    sealed class FactViewEffect {
        object ShowFact : FactViewEffect()
    }

    data class FactViewState(
        val loading: Boolean = false,
    )
}