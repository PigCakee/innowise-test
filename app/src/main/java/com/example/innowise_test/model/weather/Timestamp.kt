package com.example.innowise_test.model.weather

data class Timestamp(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)