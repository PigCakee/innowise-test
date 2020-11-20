package com.example.innowise_test.model.weather

data class ApiResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Timestamp>,
    val message: Int
)