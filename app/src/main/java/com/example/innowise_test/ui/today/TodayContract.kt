package com.example.innowise_test.ui.today

interface TodayContract {
    interface View {
        fun showSomething()
    }

    interface Presenter {

    }

    interface Repository {
        fun getTodayWeather()
    }
}