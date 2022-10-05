package com.example.retrofitregressample.network.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (

    @SerialName("email")
    var email: String,

    @SerialName("password")
    var password: String
)