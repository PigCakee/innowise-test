package com.example.innowise_test.ui.forecast

interface ForecastContract {
    interface View {
        fun showSomething()
    }

    interface Presenter {

    }

    interface Repository {
        fun getForecastWeather()
    }
}