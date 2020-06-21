package net.zovguran.lolchampionmastery

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.zovguran.lolchampionmastery.data.MasteryRecord
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecycleAdapterTest {

    @Test
    fun adapterLoadTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val records: List<MasteryRecord> = listOf(
            MasteryRecord(0, "First", "asd213", "first", 8976123, 7),
            MasteryRecord(1, "First", "asd213", "second", 3213542, 6),
            MasteryRecord(2, "First", "asd213", "third", 2345632, 6),
            MasteryRecord(3, "First", "asd213", "fourth", 1976323, 6),
            MasteryRecord(4, "First", "asd213", "fifth", 234256, 6),
            MasteryRecord(5, "Second summoner", "asd214", "first", 5415323, 7)
        )
        val adapter = MasteryItemAdapter(appContext, records)

        Assert.assertEquals(records, adapter.masteryList)
    }

}