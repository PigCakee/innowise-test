package com.example.innowise_test.model.weather

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.TypeConverters
import com.example.innowise_test.model.db.DataTypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Timestamp(
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val date: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) : Parcelable