package net.zovguran.lolchampionmastery

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.zovguran.lolchampionmastery.data.MasteryDatabase
import net.zovguran.lolchampionmastery.data.MasteryDatabaseDao
import net.zovguran.lolchampionmastery.data.MasteryRecord
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var masteryDao: MasteryDatabaseDao
    private lateinit var db: MasteryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MasteryDatabase::class.java).build()
        masteryDao = db.masteryDatabaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeMasteryAndReadInList() {
        val mastery: MasteryRecord = MasteryRecord(0, "First Guy","asd123","coolChamp",10,1)
        masteryDao.insertMasteryRecord(mastery)
        val byName = masteryDao.getStoredMasteryScoresBySummonerName("First Guy")
        assertThat(byName.first().summonerName, equalTo(mastery.summonerName))
        assertThat(byName.first().summonerId, equalTo(mastery.summonerId))
        assertThat(byName.first().championId, equalTo(mastery.championId))
        assertThat(byName.first().championPoints, equalTo(mastery.championPoints))
        assertThat(byName.first().championLevel, equalTo(mastery.championLevel))
    }
}