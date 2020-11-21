package com.example.innowise_test.model.db

import androidx.room.TypeConverter
import com.example.innowise_test.model.weather.Day
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataTypeConverter {

    @TypeConverter
    fun daysToString(list: List<Day>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToDays(data: String): List<Day> {
        return if (data.isNotEmpty()) {
            val gson = Gson()
            val listType = object : TypeToken<List<Day>>() {}.type
            gson.fromJson(data, listType)
        } else {
            emptyList()
        }
    }
}