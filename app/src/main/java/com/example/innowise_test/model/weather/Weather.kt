package com.example.innowise_test.model.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) : Parcelable