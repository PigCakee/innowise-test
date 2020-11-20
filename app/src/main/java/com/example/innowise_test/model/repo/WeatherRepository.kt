package com.example.innowise_test.model.repo

import com.example.innowise_test.ui.forecast.ForecastContract
import com.example.innowise_test.ui.today.TodayContract
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherRepository @Inject constructor() : ForecastContract.Repository,
    TodayContract.Repository {

    @Inject
    lateinit var retrofit: Retrofit

    override fun getForecastWeather() {
        TODO("Not yet implemented")
    }

    override fun getTodayWeather() {
        TODO("Not yet implemented")
    }
}