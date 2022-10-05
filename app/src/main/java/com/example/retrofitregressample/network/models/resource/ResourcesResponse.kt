package com.example.retrofitvincenttirgei.network.models.resource

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ResourcesResponse(

    @SerialName("page")
    val page: Int,

    @SerialName("per_page")
    val per_page: Int,

    @SerialName("total")
    val total: Int,

    @SerialName("total_pages")
    val total_pages: Int,

    @SerialName("data")
    val data: List<Data>,

    @SerialName("support")
    val support: Support,
)