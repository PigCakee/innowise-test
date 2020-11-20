package com.example.innowise_test.di

import com.example.innowise_test.ui.forecast.ForecastFragment
import com.example.innowise_test.ui.main.MainActivity
import com.example.innowise_test.ui.today.TodayFragment
import dagger.Subcomponent

@Subcomponent
interface TodayComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TodayComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: TodayFragment)
}
