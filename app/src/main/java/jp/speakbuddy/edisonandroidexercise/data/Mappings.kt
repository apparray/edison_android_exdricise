package jp.speakbuddy.edisonandroidexercise.data

import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.model.FactState
import jp.speakbuddy.edisonandroidexercise.network.FactResponse
import java.util.regex.Pattern

fun FactResponse.toFact(): Fact = Fact(text = fact, length = length)

fun Fact.toFactState(): FactState {
    val pattern = Pattern.compile("\\bcats\\b", Pattern.CASE_INSENSITIVE)
    val isMultipleCats = pattern.matcher(text).find()
    return FactState(
        fact = text,
        length = length,
        isMultipleCats = isMultipleCats,
        isShowingLength = length > 100
    )
}