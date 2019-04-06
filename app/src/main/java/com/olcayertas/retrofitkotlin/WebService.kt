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
class WebService {

    private val baseUrl = "https://your.api.com"
    private var service: RetrofitWithKotlin

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
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .client(okHttpBuilder())
            .build()
            .create()
    }

    fun login(userName: String, password: String, calback: (response: LoginResponse?) -> Unit): Future<Unit> {
        return doAsync {
            try {
                service.login(userName, password)
                    .execute()
                    .body()?.let {
                        calback(it)
                    } ?: calback(null)
            }
            catch (e: IOException) {
                e.printStackTrace()
                calback(null)
            }
        }
    }
}