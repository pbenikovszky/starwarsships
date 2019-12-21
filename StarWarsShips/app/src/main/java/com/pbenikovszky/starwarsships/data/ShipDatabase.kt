package com.pbenikovszky.starwarsships.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ship::class, Favourite::class], version = 4, exportSchema = false)
abstract class ShipDatabase : RoomDatabase() {

    abstract fun shipDAO(): ShipDAO

    companion object {

        @Volatile
        private var INSTANCE: ShipDatabase? = null

        fun getDatabase(context: Context): ShipDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShipDatabase::class.java,
                        "ships.db"
                    ).build()
                }
            }

            return INSTANCE!!

        }

    }

}