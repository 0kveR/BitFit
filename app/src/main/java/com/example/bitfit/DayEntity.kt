package com.example.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_table")
data class DayEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "oz") val oz: Int?,
    @ColumnInfo(name = "rating") val rating: Int?
)