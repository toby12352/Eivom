package com.example.eivom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TvShowList::class], version = 1)
abstract class TvShowDatabase: RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile private var instance: TvShowDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                TvShowDatabase::class.java,
                "favoriteTVShows.db"
            ).build()

        fun getInstance(context: Context): TvShowDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}