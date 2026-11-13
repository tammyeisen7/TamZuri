package com.tamzuri.app.data

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body req: LoginRequest): AuthResponse

    @POST("api/auth/registro")
    suspend fun registro(@Body req: RegistroRequest): AuthResponse

    @GET("api/contactos")
    suspend fun listarContactos(): List<ContactoResponse>

    @POST("api/contactos")
    suspend fun crearContacto(@Body req: ContactoRequest): ContactoResponse

    @PUT("api/contactos/{id}")
    suspend fun actualizarContacto(@Path("id") id: Long, @Body req: ContactoRequest): ContactoResponse

    @DELETE("api/contactos/{id}")
    suspend fun borrarContacto(@Path("id") id: Long): Response<Unit>
}