package com.example.retrofitregressample.network.models.resource

import com.example.retrofitvincenttirgei.network.models.resource.Data
import com.example.retrofitvincenttirgei.network.models.resource.Support
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Resource(

    @SerialName("data")
    val data: Data,

    @SerialName("support")
    val support: Support
)