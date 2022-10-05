package com.example.retrofitregressample.network.authrepository

interface AuthRepository {

    fun refreshToken(refreshToken: String): String
}
