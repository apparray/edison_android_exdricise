package jp.speakbuddy.edisonandroidexercise.fact.domain.usecase

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo

class CheckFactLengthUseCase {

    operator fun invoke(factInfo: FactInfo?, limit: Int): Boolean {
        return factInfo != null && factInfo.length > limit
    }
}