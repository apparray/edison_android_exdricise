package jp.speakbuddy.edisonandroidexercise.fact.data.remote.dto

import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import kotlinx.serialization.Serializable

@Serializable
data class FactInfoDto(
    val fact: String,
    val length: Int
)

fun FactInfoDto.toFactInfo(): FactInfo {
    return FactInfo(
        fact = fact,
        length = length
    )
}