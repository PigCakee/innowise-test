package com.example.innowise_test.model.db

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.innowise_test.model.weather.City
import com.example.innowise_test.model.weather.Day
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "weather_table")
@Parcelize
data class WeatherContainer(
    @TypeConverters(DataTypeConverter::class)
    val days: List<Day>,
    @PrimaryKey
    @Embedded
    val city: City
) : Parcelable
