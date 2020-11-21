package com.example.innowise_test.ui.today

import android.content.Context
import android.location.Location
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.repo.WeatherRepository
import com.example.innowise_test.model.weather.ApiResponse
import com.example.innowise_test.model.weather.Day
import javax.inject.Inject

class TodayPresenter @Inject constructor(val view: TodayContract.View, context: Context) :
    TodayContract.Presenter {
    private val repository: TodayContract.Repository

    init {
        repository = WeatherRepository(this, context)
    }

    fun getWeather(location: Location) {
        repository.getWeather(location)
    }

    override fun onWeatherReady(weatherContainer: WeatherContainer) {
        repository.deleteWeatherFromDB()
        repository.saveWeatherToDB(weatherContainer)

        view.onWeatherReady(weatherContainer)
    }

    override fun onCallError(e: Throwable) {
        view.onCallError(e)
    }
}