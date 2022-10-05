package com.example.retrofitregressample.network.authrepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override fun refreshToken(refreshToken: String): String {
        /* .... */
        return ""
    }
}
