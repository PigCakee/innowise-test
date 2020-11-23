package com.example.innowise_test.ui.today

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentTodayBinding
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.repo.ConnectivityReceiver
import com.example.innowise_test.model.weather.City
import com.example.innowise_test.model.weather.Day
import com.example.innowise_test.model.weather.WeatherIconFactory
import com.example.innowise_test.ui.main.MainActivity
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.view.ARGUMENT_TAG
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import java.util.*

class TodayFragment : Fragment(), TodayContract.View,
    ConnectivityReceiver.ConnectivityReceiverListener,
    SwipeRefreshLayout.OnRefreshListener {
    private val binding by contentView<FragmentTodayBinding>(R.layout.fragment_today)
    private lateinit var presenter: TodayContract.Presenter
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location
    private lateinit var navController: NavController
    private var days: ArrayList<Day> = arrayListOf()
    private var city: City = City.emptyInstance()
    private val receiver = ConnectivityReceiver()
    private var isConnected: Boolean = false

    companion object {
        const val PERMISSION_ID = 44
        const val ERROR = "Please turn on your location..."
        const val OFFLINE = "You are offline"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).todayComponent.inject(this)
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = TodayPresenter(this, requireContext())
        binding.swipeToRefreshLayout.setOnRefreshListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        onWeatherReady(WeatherContainer(days, city))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
        requireActivity().registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        checkPermissions()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onRefresh() {
        getLastLocation(isConnected)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.isConnected = isConnected
        binding.swipeToRefreshLayout.isRefreshing = true

        showNetworkMessage(isConnected)
        getLastLocation(isConnected)
    }

    override fun onCallError(e: Throwable) {
    }

    override fun onWeatherReady(weatherContainer: WeatherContainer) {
        binding.swipeToRefreshLayout.isRefreshing = false

        days = weatherContainer.days as ArrayList<Day>
        city = weatherContainer.city
        if (days.isNotEmpty()) {
            with(days.first().timestamps.first()) {
                val temp = "${this.main.temp.toInt() - 273}°C"
                val pressure = "${this.main.pressure} hPa"
                val wind = "${this.wind.speed} m/s"
                val humidity = "${this.main.humidity}%"
                var weather = weather[0].description
                weather = weather.replaceFirst(weather[0], weather[0].toUpperCase())
                val iconId = this.weather.first().id

                binding.temp.text = temp
                binding.pressure.text = pressure
                binding.wind.text = wind
                binding.humidity.text = humidity
                binding.status.text = weather
                binding.sky.setImageDrawable(WeatherIconFactory.getIcon(iconId, requireContext()))
            }

            val cityStr = "${city.name},${city.country}"
            binding.city.text = cityStr

            navController.graph.findNode(R.id.navigation_forecast)
                ?.addArgument(
                    ARGUMENT_TAG,
                    NavArgument.Builder().setDefaultValue(weatherContainer).build()
                )
        }
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        val snackbar = Snackbar.make(binding.root, OFFLINE, Snackbar.LENGTH_LONG)
        if (!isConnected) {
            snackbar.show()
        } else {
            snackbar.dismiss()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(isConnected: Boolean) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient
                    .lastLocation
                    .addOnCompleteListener { task ->
                        val location = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            this.location = location
                            (presenter as TodayPresenter).getWeather(location, isConnected)
                        }
                    }
            } else {
                Toast.makeText(requireContext(), ERROR, Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(
            locationResult: LocationResult
        ) {
            val mLastLocation = locationResult.lastLocation
            location = mLastLocation
        }
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ))
    }
}