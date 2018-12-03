package com.example.marjancvetkovic.corutinesexample.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.marjancvetkovic.corutinesexample.model.Bmf

@Database(entities = [Bmf::class], version = AppDatabase.CURRENT_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun officeDao(): BmfDao

    companion object {
        private const val DB_NAME = "office_database"
        internal const val CURRENT_VERSION = 1

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DB_NAME
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }
}