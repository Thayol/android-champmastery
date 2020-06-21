package net.zovguran.lolchampionmastery.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MasteryDatabaseDao {
    @Insert
    fun insertMasteryRecord(masteryRecord: MasteryRecord)

    @Update
    fun updateMasteryRecord(masteryRecord: MasteryRecord)

    @Delete
    fun deleteMasteryRecord(masteryRecord: MasteryRecord)

    @Query("DELETE FROM mastery_records_table WHERE 1 = 1") // note: it had bugs without WHERE
    fun purgeDatabase()

    @Query("DELETE FROM mastery_records_table WHERE summoner_id = :summonerId")
    fun deleteMasteryRecordBySummonerId(summonerId: String)

    @Query("SELECT * FROM mastery_records_table")
    fun getAllMasteryRecords() : LiveData<List<MasteryRecord>>

    @Query("SELECT * FROM mastery_records_table WHERE summoner_id = :summonerId ORDER BY champion_points DESC")
    fun getMasteryScoreBySummonerId(summonerId: String): List<MasteryRecord>

    @Query("SELECT * FROM mastery_records_table WHERE summoner_id = :summonerId ORDER BY champion_points DESC")
    fun getLiveMasteryScoreBySummonerId(summonerId: String): LiveData<List<MasteryRecord>>

    @Query("SELECT DISTINCT summoner_name FROM mastery_records_table")
    fun getStoredSummonerNames() : List<String>

    @Query("SELECT * FROM mastery_records_table WHERE `REPLACE`(UPPER(summoner_name), ' ', '') = `REPLACE`(UPPER(:summonerName), ' ', '')")
    fun getStoredMasteryScoresBySummonerName(summonerName: String) : List<MasteryRecord>
}