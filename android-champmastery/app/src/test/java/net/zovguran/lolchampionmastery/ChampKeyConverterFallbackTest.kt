package net.zovguran.lolchampionmastery

import org.junit.Assert
import org.junit.Test

class ChampKeyConverterFallbackTest {
    @Test
    fun correctConditionalFallbacks() {
        Assert.assertEquals("unique", getIdFromKeyIfLoaded("unique"))
        Assert.assertEquals("unique", getNameFromIdIfLoaded("unique"))
    }
}