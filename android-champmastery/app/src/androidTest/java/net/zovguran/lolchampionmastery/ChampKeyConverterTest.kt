package net.zovguran.lolchampionmastery

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampKeyConverterTest {
    @Test
    fun conditionalIdFromKeyWithoutLoading() {
        val key62 = 62.toString() // starting data: 62 = MonkeyKing = Wukong
        val stillKey62 = getIdFromKeyIfLoaded(key62)
        Assert.assertEquals(key62, stillKey62)
    }

    @Test
    fun conditionalIdFromKeyWithLoading() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val key62 = 62.toString()
        val monkeyKing = getIdFromKey(appContext, key62)
        Assert.assertEquals("MonkeyKing", monkeyKing)
    }

    @Test
    fun conditionalNameFromIdWithoutLoading() {
        val monkeyKing = "MonkeyKing"
        val stillMonkeyKing = getNameFromIdIfLoaded(monkeyKing)
        Assert.assertEquals(monkeyKing, stillMonkeyKing)
    }

    @Test
    fun conditionalNameFromIdWithLoading() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val monkeyKing = "MonkeyKing"
        val wukong = getNameFromId(appContext, monkeyKing)
        Assert.assertEquals("Wukong", wukong)
    }

    @Test
    fun handlingInvalidIdFromKey() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("ERROR", getIdFromKey(appContext, "notEvenANumber"))
    }

    @Test
    fun handlingInvalidNameFromId() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("ERROR", getNameFromId(appContext, "129736"))
    }
}