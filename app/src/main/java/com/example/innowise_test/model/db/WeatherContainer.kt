package com.example.innowise_test.model.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.innowise_test.model.weather.City
import com.example.innowise_test.model.weather.Day

@Entity(tableName = "weather_table")
data class WeatherContainer(
    @TypeConverters(DataTypeConverter::class)
    val days: List<Day>,
    @PrimaryKey
    @Embedded
    val city: City
)
