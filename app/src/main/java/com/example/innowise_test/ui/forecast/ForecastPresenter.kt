package com.example.innowise_test.ui.forecast

import com.example.innowise_test.model.repo.WeatherRepository

class ForecastPresenter(private val view: ForecastContract.View): ForecastContract.Presenter {
    private val repository: ForecastContract.Repository

    init {
        repository = WeatherRepository()
    }
}