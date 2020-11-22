package com.example.innowise_test.ui.today

import android.location.Location
import com.example.innowise_test.model.db.WeatherContainer

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
        fun getWeather(location: Location, isConnected: Boolean)
        fun manageWeatherInDatabase(weatherContainer: WeatherContainer)
        fun saveWeatherToDB(weatherContainer: WeatherContainer)
    }
}