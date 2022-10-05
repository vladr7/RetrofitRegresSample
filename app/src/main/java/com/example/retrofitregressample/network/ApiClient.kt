package com.example.retrofitregressample.network

import com.example.retrofitregressample.network.interceptors.AuthInterceptor
import com.example.retrofitregressample.network.sessionmanager.SessionManager
import com.example.retrofitregressample.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val sessionManager: SessionManager
) {

    val contentType = "application/json".toMediaType()
    @OptIn(ExperimentalSerializationApi::class)
    val kotlinxConverterFactory = Json.asConverterFactory(contentType)
    private lateinit var apiService: ApiService

    fun getApiService(): ApiService {

        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(kotlinxConverterFactory)
                .client(okhttpClient())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

    /**
     * Initialize OkhttpClient with our interceptor
     */
    private fun okhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .build()
    }
}