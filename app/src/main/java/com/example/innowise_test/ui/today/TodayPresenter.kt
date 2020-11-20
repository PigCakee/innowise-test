package com.example.innowise_test.ui.today

import com.example.innowise_test.model.repo.WeatherRepository
import javax.inject.Inject

class TodayPresenter @Inject constructor(val view: TodayContract.View) :
    TodayContract.Presenter {
    private val repository: TodayContract.Repository

    init {
        repository = WeatherRepository()
    }
}