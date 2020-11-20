package com.example.innowise_test.ui.today

import com.example.innowise_test.model.repo.WeatherRepository

class TodayPresenter(private val view: TodayContract.View) : TodayContract.Presenter {
    private val repository: TodayContract.Repository

    init {
        repository = WeatherRepository()
    }
}