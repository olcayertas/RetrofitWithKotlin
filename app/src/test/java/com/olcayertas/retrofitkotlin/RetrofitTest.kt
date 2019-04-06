/*
 * Created by Olcay Ertaş on 3/25/19 12:06 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/24/19 11:15 PM
 */

package com.olcayertas.retrofitkotlin

import com.google.gson.GsonBuilder
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

import org.robolectric.RobolectricTestRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class RetrofitTest {

    private val userName = "your-user-name"
    private val password = "your-password"

    @Test
    fun loginTest() {
        WebService().login(userName, password) {
            assertNotNull("Login service returned null!", it)
            println("JSON: " + GsonBuilder().setPrettyPrinting().create().toJson(it))
        }.get()
    }
}
