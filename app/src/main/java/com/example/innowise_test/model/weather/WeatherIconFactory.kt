package com.example.innowise_test.model.weather

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.innowise_test.R

class WeatherIconFactory {
    companion object {
        fun getIcon(weatherId: Int, context: Context): Drawable? {
            return when (weatherId) {
                in (200..232) -> ContextCompat.getDrawable(context, R.drawable.ic_thunderstorm)
                in (300..321) -> ContextCompat.getDrawable(context, R.drawable.ic_rain)
                in (500..504) -> ContextCompat.getDrawable(context, R.drawable.ic_lightrain)
                511 -> ContextCompat.getDrawable(context, R.drawable.ic_snow)
                in (520..531) -> ContextCompat.getDrawable(context, R.drawable.ic_rain)
                in (600..622) -> ContextCompat.getDrawable(context, R.drawable.ic_snow)
                in (700..781) -> ContextCompat.getDrawable(context, R.drawable.ic_fog)
                800 -> ContextCompat.getDrawable(context, R.drawable.ic_sun)
                801 -> ContextCompat.getDrawable(context, R.drawable.ic_fewclouds)
                802 -> ContextCompat.getDrawable(context, R.drawable.ic_clouds)
                in (803..804) -> ContextCompat.getDrawable(context, R.drawable.ic_overcastclouds)
                else -> ContextCompat.getDrawable(context, R.drawable.ic_overcastclouds)
            }
        }
    }
}