package jp.speakbuddy.edisonandroidexercise.fact.domain.usecase

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo

class CheckFactContentUseCase {

    operator fun invoke(factInfo: FactInfo?, keyword: String): Boolean {
        return factInfo?.fact?.contains(keyword, ignoreCase = true) == true
    }
}