/*
 * Created by Olcay Ertaş on 3/24/19 11:46 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/24/19 11:46 PM
 */

package com.olcayertas.retrofitkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.olcayertas.retrofitkotlin", appContext.packageName)
    }
}
