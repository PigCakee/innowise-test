package com.example.innowise_test.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.innowise_test.MainApplication
import com.example.innowise_test.R
import com.example.innowise_test.databinding.ActivityMainBinding
import com.example.innowise_test.di.ForecastComponent
import com.example.innowise_test.di.TodayComponent

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var todayComponent: TodayComponent
    lateinit var forecastComponent: ForecastComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_today, R.id.navigation_forecast
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun inject() {
        todayComponent = (application as MainApplication).appComponent.todayComponent().create()
        todayComponent.inject(this)

        forecastComponent =
            (application as MainApplication).appComponent.forecastComponent().create()
        forecastComponent.inject(this)
    }
}
