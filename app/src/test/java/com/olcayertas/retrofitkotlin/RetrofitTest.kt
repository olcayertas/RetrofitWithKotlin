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

    private val userName = "olcayertas"

    private fun toJson(src: Any?): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(src)
    }

    @Test
    fun reposTest() {
        Api().repos(userName) { list ->
            assertNotNull(list)
            println("Repos: " + toJson(list))
        }.get()
    }
}
