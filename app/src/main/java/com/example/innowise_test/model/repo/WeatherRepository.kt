package com.example.innowise_test.model.repo

import android.location.Location
import com.example.innowise_test.api.WeatherApi
import com.example.innowise_test.model.weather.ApiResponse
import com.example.innowise_test.ui.today.TodayContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val presenter: TodayContract.Presenter) :
    TodayContract.Repository {

    override fun getWeather(location: Location) {
        val weatherApi = WeatherApi()
        val observable =
            weatherApi.getWeather(location.latitude.toString(), location.longitude.toString())
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ApiResponse> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(apiResponse: ApiResponse?) {
                    apiResponse?.let { onWeatherReady(it) }
                }

                override fun onError(e: Throwable?) {
                    e?.let { onCallError(it) }
                }

                override fun onComplete() {
                }
            })
    }

    fun onWeatherReady(apiResponse: ApiResponse) {
        presenter.onWeatherReady(apiResponse)
    }

    fun onCallError(e: Throwable) {
        presenter.onCallError(e)
    }
}