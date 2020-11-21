package com.example.innowise_test.ui.today

import android.location.Location
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.weather.ApiResponse
import com.example.innowise_test.model.weather.City
import com.example.innowise_test.model.weather.Day

interface TodayContract {
    interface View {
        fun onWeatherReady(weatherContainer: WeatherContainer)
        fun onCallError(e: Throwable)
    }

    interface Presenter {
        fun onWeatherReady(weatherContainer: WeatherContainer)
        fun onCallError(e: Throwable)
    }

    interface Repository {
        fun getWeather(location: Location)
        fun deleteWeatherFromDB()
        fun saveWeatherToDB(weatherContainer: WeatherContainer)
    }
}