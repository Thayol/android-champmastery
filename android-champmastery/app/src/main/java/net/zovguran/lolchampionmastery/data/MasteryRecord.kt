package net.zovguran.lolchampionmastery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mastery_records_table")
data class MasteryRecord (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "summoner_name")
    var summonerName: String="",

    @ColumnInfo(name = "summoner_id")
    var summonerId: String="",

    @ColumnInfo(name = "champion_id")
    var championId: String="",

    @ColumnInfo(name = "champion_points")
    var championPoints: Int=0,

    @ColumnInfo(name = "champion_level")
    var championLevel: Int=0) {

    override fun toString(): String {
        return "$summonerName: $championId ($championPoints points)"
    }
}