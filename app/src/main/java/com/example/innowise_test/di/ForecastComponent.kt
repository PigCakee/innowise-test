package com.example.innowise_test.di

import com.example.innowise_test.ui.forecast.ForecastFragment
import com.example.innowise_test.ui.main.MainActivity
import dagger.Subcomponent

@Subcomponent
interface ForecastComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ForecastComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ForecastFragment)
}
