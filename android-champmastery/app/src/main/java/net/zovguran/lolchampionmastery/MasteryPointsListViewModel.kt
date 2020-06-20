package net.zovguran.lolchampionmastery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.zovguran.lolchampionmastery.data.MasteryDatabase
import net.zovguran.lolchampionmastery.data.MasteryRecord
import net.zovguran.lolchampionmastery.data.MasteryRecordRepository

class MasteryPointsListViewModel(application: Application, summonerId: String) : AndroidViewModel(application) {
    private val repository: MasteryRecordRepository
    val records: LiveData<List<MasteryRecord>>

    init {
        val masteryDatabaseDao = MasteryDatabase.getDatabase(application).masteryDatabaseDao()
        repository = MasteryRecordRepository(masteryDatabaseDao)
        // allRecords = repository.allRecords
        records = repository.getLiveMasteryScoreBySummonerId(summonerId)
    }

    fun insertMasteryRecord(masteryRecord: MasteryRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertMasteryRecord(masteryRecord)
    }

    /*
    fun getMasteryRecordBySummonerId(summonerId: String) : List<MasteryRecord> {
        return repository.getMasteryScoreBySummonerId(summonerId)
    }
    */
}