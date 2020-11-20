package com.example.innowise_test.model.weather

import com.google.gson.annotations.SerializedName

data class Weather(
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val date: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)