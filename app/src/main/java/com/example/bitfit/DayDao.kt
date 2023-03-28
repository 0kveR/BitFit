package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Query("SELECT * FROM day_table")
    fun getAll(): List<DayEntity>

    @Insert
    fun insertAll(days: List<DayEntity>)

    @Query("DELETE FROM day_table")
    fun deleteAll()
}