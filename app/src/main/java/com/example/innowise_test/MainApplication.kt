package com.example.innowise_test

import android.app.Application
import com.example.innowise_test.di.AppComponent
import com.example.innowise_test.di.DaggerAppComponent

open class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}
