package com.example.innowise_test.di

import android.content.Context
import android.content.SharedPreferences
import com.example.innowise_test.model.db.WeatherDatabase
import com.example.innowise_test.utils.view.APP_PREFERENCES
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun provideWeatherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideLocationsDatabase(context: Context): WeatherDatabase =
        WeatherDatabase.getDatabase(context)
}
