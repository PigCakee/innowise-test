package com.example.innowise_test.ui.forecast

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentForecastBinding
import com.example.innowise_test.ui.main.MainActivity
import com.example.innowise_test.utils.inflaters.contentView
import javax.inject.Inject

class ForecastFragment : Fragment(), ForecastContract.View {
    private val binding by contentView<FragmentForecastBinding>(R.layout.fragment_forecast)
    private lateinit var presenter: ForecastContract.Presenter

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

    override fun showSomething() {
        TODO("Not yet implemented")
    }
}