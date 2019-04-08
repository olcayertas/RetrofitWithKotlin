package com.olcayertas.retrofitkotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/users/{userName}/repos")
    fun repos(@Path("userName") userName: String): Call<List<Repo>>
}