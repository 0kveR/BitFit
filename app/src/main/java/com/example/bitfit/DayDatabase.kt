package com.example.bitfit

import android.content.Context
import androidx.room.Room

class DayDatabase internal constructor(context: Context) {
    private val db: AppDatabase

    fun getAllDays(): MutableList<DayEntity> {
        return db.dayDao().getAll() as MutableList<DayEntity>
    }

    fun updateDays(days: List<DayEntity>) {
        db.dayDao().deleteAll()
        db.dayDao().insertAll(days)
    }

    init {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "day-database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}