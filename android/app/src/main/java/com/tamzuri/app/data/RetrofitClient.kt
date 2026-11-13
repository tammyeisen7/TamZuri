package com.tamzuri.app.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitClient {

    // 10.0.2.2 es "el ordenador" visto desde el emulador
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val peticion = chain.request().newBuilder()
            SessionManager.token?.let { peticion.addHeader("Authorization", "Bearer $it") }
            chain.proceed(peticion.build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

fun Throwable.mensajeError(): String = when (this) {
    is HttpException -> try {
        JSONObject(response()?.errorBody()?.string() ?: "").getString("message")
    } catch (e: Exception) {
        "Error ${code()}"
    }
    is IOException -> "No se puede conectar con el servidor"
    else -> message ?: "Error desconocido"
}