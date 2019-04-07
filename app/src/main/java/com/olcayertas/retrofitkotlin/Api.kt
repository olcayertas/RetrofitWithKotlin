/*
 * Created by Olcay Ertaş on 3/24/19 8:35 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/24/19 8:34 PM
 */

package com.olcayertas.retrofitkotlin

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


@Suppress("unused")
class Api {

    private val baseUrl = "https://api.github.com"
    private var api: GitHubApi

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun okHttpBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())
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

    fun repos(userName: String, calback: (list: List<Repo>?) -> Unit): Future<Unit> {
        return doAsync {
            try {
                val response = api.repos(userName).execute()

                if (response.isSuccessful) {
                    response.body()?.let {
                        calback(it)
                    } ?: calback(null)
                }

            } catch (e: IOException) {
                e.printStackTrace()
                calback(null)
            }
        }
    }
}