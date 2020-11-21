package com.example.innowise_test.model.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val country: String,
    val name: String,
) : Parcelable {
    companion object {
        fun emptyInstance() = City(
            country = "",
            name = ""
        )
    }
}