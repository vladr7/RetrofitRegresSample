package com.example.retrofitregressample.network

import com.example.retrofitregressample.network.models.login.LoginRequest
import com.example.retrofitregressample.network.models.login.LoginResponse
import com.example.retrofitregressample.network.models.resource.Resource
import com.example.retrofitregressample.utils.Constants
import com.example.retrofitvincenttirgei.network.models.resource.ResourcesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse> // the new God in town

    @GET(Constants.RESOURCE_URL)
    suspend fun getResources(): Response<ResourcesResponse>

    @GET("${Constants.RESOURCE_URL}/{resourceId}")
    suspend fun getResource(
        @Path("resourceId") resourceId: Int,
    ): Response<Resource>

}