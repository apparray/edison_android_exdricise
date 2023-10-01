package jp.speakbuddy.edisonandroidexercise.fact.presentation

data class FactUiState(
    val fact: String = "",
    val length: String? = null,
    val showMultipleCats: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)