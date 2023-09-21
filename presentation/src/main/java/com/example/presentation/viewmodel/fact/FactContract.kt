package com.example.presentation.viewmodel.fact

class FactContract {
    sealed class FactEvent {
        object RefreshData : FactEvent()
    }

    data class FactViewState(
        val loading: Boolean = false,
        val retry: Boolean = false,
        val errorMsg: Int = 0,
        val fact: String = "",
        val length: String = "",
        val showHintCats: Boolean = false,
        val showHintLength: Boolean = false,
    )
}