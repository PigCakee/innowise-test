package com.example.innowise_test.ui.today

import android.location.Location
import com.example.innowise_test.model.repo.WeatherRepository
import com.example.innowise_test.model.weather.ApiResponse
import com.example.innowise_test.model.weather.Day
import javax.inject.Inject

class TodayPresenter @Inject constructor(val view: TodayContract.View) :
    TodayContract.Presenter {
    private val repository: TodayContract.Repository

    init {
        repository = WeatherRepository(this)
    }

    fun getWeather(location: Location) {
        repository.getWeather(location)
    }

    override fun onWeatherReady(apiResponse: ApiResponse) {
        val days = mutableListOf<Day>()
        val day = Day()
        var dayStr = apiResponse.list.first().date.split(' ').first()

        apiResponse.list.forEach { timestamp ->
            if (timestamp.date.split(' ').first() == dayStr) {
                day.timestamps.add(timestamp)
            } else {
                days.add(day)
                day.timestamps.clear()
                day.timestamps.add(timestamp)
                dayStr = timestamp.date.split(' ').first()
            }
        }
        days.add(day)

        view.onWeatherReady(days, apiResponse.city)
    }

    override fun onCallError(e: Throwable) {
        view.onCallError(e)
    }
}