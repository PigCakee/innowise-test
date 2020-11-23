package com.example.innowise_test.model.weather

class WeatherWindFactory {
    companion object {
        fun getWindDirection(degree: Int): String {
            return when (degree) {
                in (0..22) -> "N"
                in (23..68) -> "NE"
                in (69..112) -> "E"
                in (113..158) -> "SE"
                in (159..202) -> "S"
                in (203..248) -> "SW"
                in (249..315) -> "NE"
                in (316..359) -> "N"
                else -> "N"
            }
        }
    }
}