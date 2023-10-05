package jp.speakbuddy.edisonandroidexercise.ui

import jp.speakbuddy.edisonandroidexercise.domain.GetFactStateUseCase
import jp.speakbuddy.edisonandroidexercise.model.FactState

class GetFactStateUseCaseMock : GetFactStateUseCase {
    override suspend fun getFactState(): FactState {
        return FactState(
            fact = "new fact",
            length = 8,
            isMultipleCats = false,
            isShowingLength = false
        )
    }

}