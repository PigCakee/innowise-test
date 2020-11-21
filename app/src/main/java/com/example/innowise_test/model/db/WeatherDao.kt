package com.example.innowise_test.model.db

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface WeatherDao {
    @Query("SELECT * from weather_table")
    fun getWeather(): Single<WeatherContainer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(weatherContainer: WeatherContainer)

    @Query("DELETE FROM weather_table")
    fun deleteAll()

    @Delete
    fun delete(weatherContainer: WeatherContainer)
}
