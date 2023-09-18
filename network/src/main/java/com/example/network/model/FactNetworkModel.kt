package com.example.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FactNetworkModel(
    @field:Json(name ="fact") val fact: String,
    @field:Json(name ="length") val length: Int
)