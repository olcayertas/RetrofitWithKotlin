@file:Suppress("unused")

package com.olcayertas.retrofitkotlin

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitWithKotlin {

    @POST("/login/{username}/{password}")
    fun login(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<LoginResponse>
}