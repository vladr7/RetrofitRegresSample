package com.example.retrofitvincenttirgei.network.models.resource

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Support(

    @SerialName("url")
    val url: String,

    @SerialName("text")
    val text: String,
)