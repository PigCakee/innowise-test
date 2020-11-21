package com.example.innowise_test.ui.today

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
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
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentTodayBinding
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.weather.City
import com.example.innowise_test.model.weather.Day
import com.example.innowise_test.ui.main.MainActivity
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.view.ARGUMENT_TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import java.util.ArrayList

class TodayFragment : Fragment(), TodayContract.View {
    private val binding by contentView<FragmentTodayBinding>(R.layout.fragment_today)
    private lateinit var presenter: TodayContract.Presenter
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location
    private lateinit var navController: NavController
    private var days: ArrayList<Day> = arrayListOf()
    private var city: City = City.emptyInstance()

    companion object {
        const val PERMISSION_ID = 44
        const val ERROR = "Please turn on your location..."
        const val DAYS = "days"
        const val CITY = "city"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).todayComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = TodayPresenter(this, requireContext())

        savedInstanceState?.getParcelableArrayList<Day>(DAYS)?.let { days = it}
        savedInstanceState?.getParcelable<City>(CITY)?.let { city = it}

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(DAYS, days as ArrayList<out Parcelable>)
        outState.putParcelable(CITY, city)
    }

    override fun onCallError(e: Throwable) {
        Toast.makeText(requireContext(), e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onWeatherReady(weatherContainer: WeatherContainer) {
        days = weatherContainer.days as ArrayList<Day>
        city = weatherContainer.city
        if (days.isNotEmpty()) {
            with(days.first().timestamps.first()) {
                val temp = "${this.main.temp.toInt() - 273}°C"
                val pressure = "${this.main.pressure} hPa"
                val wind = "${this.wind.speed} m/s"
                val humidity = "${this.main.humidity}%"

                binding.temp.text = temp
                binding.pressure.text = pressure
                binding.wind.text = wind
                binding.humidity.text = humidity
            }

            val cityStr = "${city.name},${city.country}"
            binding.city.text = cityStr

            navController.graph.findNode(R.id.navigation_forecast)
                ?.addArgument(
                    ARGUMENT_TAG,
                    NavArgument.Builder().setDefaultValue(days).build()
                )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
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
                            (presenter as TodayPresenter).getWeather(location)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }
}