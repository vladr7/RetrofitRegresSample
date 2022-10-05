package com.example.retrofitvincenttirgei.network.models.resource

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Data(

    @SerialName("id")
    val id: Int,

    @SerialName("color")
    val color: String,

    @SerialName("name")
    val name: String,

    @SerialName("pantone_value")
    val pantone_value: String,

    @SerialName("year")
    val year: Int
)