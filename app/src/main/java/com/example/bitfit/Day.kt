package com.example.bitfit

data class Day(val date: String?, val oz: Int?, val rating: Int?) {
     fun convertEntity(entity: DayEntity) : Day {
        return Day(
            entity.date,
            entity.oz,
            entity.rating
        )
    }
}