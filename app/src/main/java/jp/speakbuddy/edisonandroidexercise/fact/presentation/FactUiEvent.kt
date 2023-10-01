package jp.speakbuddy.edisonandroidexercise.fact.presentation

sealed class FactUiEvent {
    data class UpdateFact(val complete: () -> Unit): FactUiEvent()
}