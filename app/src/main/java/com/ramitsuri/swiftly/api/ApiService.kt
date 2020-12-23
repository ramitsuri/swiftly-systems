package com.ramitsuri.swiftly.api

import com.google.gson.GsonBuilder
import com.ramitsuri.swiftly.entities.ManagerSpecialsResponse
import com.ramitsuri.swiftly.serializer.BigDecimalAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.math.BigDecimal

interface ApiService {
    @GET("backup")
    suspend fun get(): Response<ManagerSpecialsResponse>

    companion object {
        private const val BASE_URL =
            "https://raw.githubusercontent.com/Swiftly-Systems/code-exercise-android/master/"

        fun create(): ApiService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(
                    BigDecimal::class.java,
                    BigDecimalAdapter()
                )
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}