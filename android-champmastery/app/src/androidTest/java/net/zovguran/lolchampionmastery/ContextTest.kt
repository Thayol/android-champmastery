package net.zovguran.lolchampionmastery

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ContextTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("net.zovguran.lolchampionmastery", appContext.packageName)
    }

    @Test
    fun allCustomJsonAccessible() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertNotNull(appContext.assets.open("champid.json").bufferedReader().use { it.readText() })
        assertNotNull(appContext.assets.open("champname.json").bufferedReader().use { it.readText() })
    }
}