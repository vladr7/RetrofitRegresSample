package com.example.retrofitregressample.di

import android.content.Context
import android.content.SharedPreferences
import com.example.retrofitregressample.network.authrepository.AuthRepository
import com.example.retrofitregressample.network.authrepository.AuthRepositoryImpl
import com.example.retrofitregressample.network.interceptors.AuthInterceptor
import com.example.retrofitregressample.network.sessionmanager.SessionManager
import com.example.retrofitregressample.utils.AppSharedPreferences
import com.example.retrofitregressample.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(AppSharedPreferences.SHARED_PREFS, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppSharedPreferences(
        sharedPreferences: SharedPreferences
    ) = AppSharedPreferences(sharedPreferences)

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()

    @Singleton
    @Provides
    fun provideSessionManager(
        appSharedPreferences: AppSharedPreferences,
        authRepository: AuthRepository
    ): SessionManager = SessionManager(
        pref = appSharedPreferences,
        authRepository = authRepository
    )

    @Singleton
    @Provides
    fun provideAuthInterceptorImpl(
        sessionManager: SessionManager
    ): AuthInterceptor = AuthInterceptor(sessionManager = sessionManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

//    private val contentType = "application/json".toMediaType()
//    @OptIn(ExperimentalSerializationApi::class)
//    private val kotlinxConverterFactory = Json.asConverterFactory(contentType)

//    @Singleton
//    @Provides
//    fun provideRetrofit(
//        okHttpClient: OkHttpClient
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(Constants.BASE_URL)
//        .client(okHttpClient)
//        .addConverterFactory(kotlinxConverterFactory)
//        .build()
//
}
