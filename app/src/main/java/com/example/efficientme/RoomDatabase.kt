package com.example.efficientme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class, Profile::class, Break::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    abstract fun profileDao(): ProfileDao
    abstract fun breaksDao(): BreaksDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "EfficientMe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}