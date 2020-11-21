package com.example.innowise_test.model.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(
    val deg: Int,
    val speed: Double
) : Parcelable