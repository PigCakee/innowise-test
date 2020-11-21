package com.example.innowise_test.model.weather

import android.os.Parcelable
import androidx.room.TypeConverters
import com.example.innowise_test.model.db.DataTypeConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Day (
    var timestamps: MutableList<Timestamp> = mutableListOf()
) : Parcelable