package com.example.bitfit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DayEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}

