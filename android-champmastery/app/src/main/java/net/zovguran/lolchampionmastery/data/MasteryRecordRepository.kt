package net.zovguran.lolchampionmastery.data

import androidx.lifecycle.LiveData

class MasteryRecordRepository(private val masteryDatabaseDao: MasteryDatabaseDao) {
    fun getMasteryScoreBySummonerId(summonerId: String) : List<MasteryRecord> {
        return masteryDatabaseDao.getMasteryScoreBySummonerId(summonerId)
    }
    fun getLiveMasteryScoreBySummonerId(summonerId: String) : LiveData<List<MasteryRecord>>
    {
        return masteryDatabaseDao.getLiveMasteryScoreBySummonerId(summonerId)
    }

    fun deleteMasteryRecordBySummonerId(summonerId: String) {
        masteryDatabaseDao.deleteMasteryRecordBySummonerId(summonerId)
    }

    val allRecords = masteryDatabaseDao.getAllMasteryRecords()

    fun insertMasteryRecord(masteryRecord: MasteryRecord) {
        masteryDatabaseDao.insertMasteryRecord(masteryRecord)
    }
    fun updateMasteryRecord(masteryRecord: MasteryRecord) {
        masteryDatabaseDao.updateMasteryRecord(masteryRecord)
    }
    fun deleteMasteryRecord(masteryRecord: MasteryRecord) {
        masteryDatabaseDao.deleteMasteryRecord(masteryRecord)
    }
}