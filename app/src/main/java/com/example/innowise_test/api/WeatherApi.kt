package com.example.innowise_test.api

import com.example.innowise_test.utils.view.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    fun getWeather(@Query("q=") city: String, @Query("appid=") apiKey: String = API_KEY)
}