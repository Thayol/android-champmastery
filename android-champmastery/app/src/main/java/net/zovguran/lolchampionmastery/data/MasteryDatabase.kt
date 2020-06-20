package net.zovguran.lolchampionmastery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MasteryRecord::class], version = 1, exportSchema = false)
public abstract class MasteryDatabase : RoomDatabase() {

    abstract fun masteryDatabaseDao(): MasteryDatabaseDao

    companion object {
        // singleton
        @Volatile
        private var INSTANCE: MasteryDatabase? = null

        fun getDatabase(context: Context): MasteryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MasteryDatabase::class.java,
                        "mastery_database"
                    ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}