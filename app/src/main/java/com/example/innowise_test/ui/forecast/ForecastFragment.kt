package com.example.innowise_test.ui.forecast

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentForecastBinding
import com.example.innowise_test.model.db.WeatherContainer
import com.example.innowise_test.model.weather.Day
import com.example.innowise_test.ui.adapters.DayAdapter
import com.example.innowise_test.ui.main.MainActivity
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.view.ARGUMENT_TAG

class ForecastFragment : Fragment(), ForecastContract.View {
    private val binding by contentView<FragmentForecastBinding>(R.layout.fragment_forecast)
    private lateinit var presenter: ForecastContract.Presenter
    private lateinit var navController: NavController
    private var weatherContainer: WeatherContainer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).forecastComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = ForecastPresenter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        weatherContainer = arguments?.getParcelable(ARGUMENT_TAG)

        weatherContainer?.let {
            val adapter = DayAdapter(it.days, requireContext())
            binding.recyclerView.adapter = adapter
        }
    }

    override fun showSomething() {
        TODO("Not yet implemented")
    }
}