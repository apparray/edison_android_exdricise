package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FactDataModel(
    val fact: String,
    val length: Int
)
