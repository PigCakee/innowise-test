package com.example.innowise_test.model.repo

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.WorkerThread
import com.example.innowise_test.api.WeatherApi
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.db.WeatherDatabase
import com.example.innowise_test.model.weather.ApiResponse
import com.example.innowise_test.model.weather.Day
import com.example.innowise_test.ui.today.TodayContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableMaybeObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.function.Consumer
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val presenter: TodayContract.Presenter,
    context: Context
) : TodayContract.Repository {
    private val weatherDatabase: WeatherDatabase = WeatherDatabase.getDatabase(context)
    private var weatherDao = weatherDatabase.weatherDao()

    override fun getWeather(location: Location) {
        getWeatherFromDB()
        getWeatherFromNet(location)
    }

    private fun getWeatherFromDB() {
        weatherDao.getWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<WeatherContainer>() {
                override fun onSuccess(it: WeatherContainer?) {
                    if (it != null) {
                        presenter.onWeatherReady(it)
                    }//
                }

                override fun onError(e: Throwable?) {
                }
            })
    }

    private fun getWeatherFromNet(location: Location) {
        val weatherApi = WeatherApi()
        val observable =
            weatherApi.getWeather(location.latitude.toString(), location.longitude.toString())
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ApiResponse> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(apiResponse: ApiResponse?) {
                    apiResponse?.let { convertResponseToWeather(it) }?.let { onWeatherReady(it) }
                }

                override fun onError(e: Throwable?) {
                    e?.let { onCallError(it) }
                }

                override fun onComplete() {
                }
            })
    }

    fun onWeatherReady(weatherContainer: WeatherContainer) {
        presenter.onWeatherReady(weatherContainer)
    }

    fun onCallError(e: Throwable) {
        presenter.onCallError(e)
    }

    private fun convertResponseToWeather(apiResponse: ApiResponse): WeatherContainer {
        val days = mutableListOf<Day>()
        val day = Day(mutableListOf())
        var dayStr = apiResponse.list.first().date.split(' ').first()

        apiResponse.list.forEach { timestamp ->
            if (timestamp.date.split(' ').first() == dayStr) {
                day.timestamps.add(timestamp)
            } else {
                days.add(day)
                day.timestamps.clear()
                day.timestamps.add(timestamp)
                dayStr = timestamp.date.split(' ').first()
            }
        }
        days.add(day)
        return WeatherContainer(days, apiResponse.city)
    }

    override fun deleteWeatherFromDB() {
        Completable.fromRunnable { weatherDao.deleteAll() }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun saveWeatherToDB(weatherContainer: WeatherContainer) {
        Completable.fromRunnable { weatherDao.insert(weatherContainer) }.subscribeOn(Schedulers.io()).subscribe()//
    }
}