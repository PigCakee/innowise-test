package com.example.innowise_test.model.repo

import com.example.innowise_test.ui.forecast.ForecastContract
import com.example.innowise_test.ui.today.TodayContract

class WeatherRepository : ForecastContract.Repository, TodayContract.Repository {
    override fun getForecastWeather() {
        TODO("Not yet implemented")
    }

    override fun getTodayWeather() {
        TODO("Not yet implemented")
    }
}