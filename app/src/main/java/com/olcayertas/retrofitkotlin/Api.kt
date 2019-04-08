/*
 * Created by Olcay Ertaş on 3/24/19 8:35 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/24/19 8:34 PM
 */

package com.olcayertas.retrofitkotlin

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

fun toJson(src: Any?): String {
    return GsonBuilder().setPrettyPrinting().create().toJson(src)
}

class Api(private var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC) {

    private val baseUrl = "https://api.github.com"
    private val api: GitHubApi

    private fun loggingInterceptor(level: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = level
        return interceptor
    }

    private fun okHttpBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.MICROSECONDS)
            .readTimeout(15, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor(logLevel))
            .build()
    }

    init {
        val converter = GsonConverterFactory.create()
        api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .client(okHttpBuilder())
            .build()
            .create()
    }

    fun repos(
        userName: String,
        onSuccess: (list: List<Repo>?) -> Unit,
        onFailure: (message: String?) -> Unit): Future<Unit> {
        return runAsync(api.repos(userName), onSuccess, onFailure)
    }

    private fun <T> runAsync(
        call: retrofit2.Call<T>,
        onSuccess: (T?) -> Unit,
        onFailure: (message: String?) -> Unit) : Future<Unit> {
        return doAsync {
            try {
                val response = call.execute()
                when {
                    response.isSuccessful -> response.body()?.let {
                        onSuccess(it)
                    }
                    else -> {
                        onFailure(response.raw().message())
                    }
                }
            } catch (e: IOException) {
                if (e is SocketTimeoutException) {
                    onFailure("Response time out!")
                } else {
                    onFailure(e.message)
                }
            }
        }
    }
}