package com.example.innowise_test.ui.today

import android.content.Context
import android.location.Location
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.repo.WeatherRepository
import javax.inject.Inject

class TodayPresenter @Inject constructor(val view: TodayContract.View, context: Context) :
    TodayContract.Presenter {
    private val repository: TodayContract.Repository

    init {
        repository = WeatherRepository(this, context)
    }

    fun getWeather(location: Location, isConnected: Boolean) {
        repository.getWeather(location, isConnected)
    }

    override fun onWeatherReady(weatherContainer: WeatherContainer) {
        repository.manageWeatherInDatabase(weatherContainer)
        view.onWeatherReady(weatherContainer)
    }

    override fun onCallError(e: Throwable) {
        view.onCallError(e)
    }
}