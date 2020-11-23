package com.example.innowise_test.model.repo

import android.content.Context
import android.location.Location
import com.example.innowise_test.api.WeatherApi
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.db.WeatherDatabase
import com.example.innowise_test.model.weather.ApiResponse
import com.example.innowise_test.model.weather.Day
import com.example.innowise_test.model.weather.Timestamp
import com.example.innowise_test.ui.today.TodayContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val presenter: TodayContract.Presenter,
    context: Context
) : TodayContract.Repository {
    private val weatherDatabase: WeatherDatabase = WeatherDatabase.getDatabase(context)
    private var weatherDao = weatherDatabase.weatherDao()

    override fun getWeather(location: Location, isConnected: Boolean) {
        if (isConnected) {
            getWeatherFromDB()
            getWeatherFromNet(location)
        } else {
            getWeatherFromDB()
        }
    }

    private fun getWeatherFromDB() {
        weatherDao.getWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<WeatherContainer>() {
                override fun onSuccess(it: WeatherContainer?) {
                    if (it != null) {
                        presenter.onWeatherReady(it)
                    }
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
        var dayStr = apiResponse.list.first().date.split(' ').first()
        val day = Day(mutableListOf())

        for ((index, timestamp) in apiResponse.list.withIndex()) {
            if (timestamp.date.split(' ').first() == dayStr) {
                day.timestamps.add(timestamp)
            } else {
                val tempDay = Day(mutableListOf())
                tempDay.timestamps = day.timestamps
                days.add(tempDay)
                day.timestamps = mutableListOf()
                day.timestamps.add(timestamp)
                dayStr = timestamp.date.split(' ').first()
            }
            if (index == apiResponse.list.indexOf(apiResponse.list.last())) {
                days.add(day)
            }
        }
        return WeatherContainer(days, apiResponse.city)
    }

    override fun manageWeatherInDatabase(weatherContainer: WeatherContainer) {
        Completable.fromRunnable { weatherDao.deleteAll() }.subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver{
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onComplete() {
                Completable.fromRunnable { weatherDao.insert(weatherContainer) }.subscribeOn(Schedulers.io()).subscribe()
            }

            override fun onError(e: Throwable?) {
            }
        })
    }

    override fun saveWeatherToDB(weatherContainer: WeatherContainer) {
        Completable.fromRunnable { weatherDao.insert(weatherContainer) }.subscribeOn(Schedulers.io()).subscribe()
    }
}