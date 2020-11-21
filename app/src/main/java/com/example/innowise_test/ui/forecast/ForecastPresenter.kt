package com.example.innowise_test.ui.forecast

import com.example.innowise_test.model.repo.WeatherRepository
import javax.inject.Inject

class ForecastPresenter @Inject constructor(private val view: ForecastContract.View) :
    ForecastContract.Presenter {

}