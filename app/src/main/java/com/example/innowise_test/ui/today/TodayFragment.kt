package com.example.innowise_test.ui.today

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentTodayBinding
import com.example.innowise_test.ui.main.MainActivity
import com.example.innowise_test.utils.inflaters.contentView
import javax.inject.Inject

class TodayFragment : Fragment(), TodayContract.View {
    private val binding by contentView<FragmentTodayBinding>(R.layout.fragment_today)
    private lateinit var presenter: TodayContract.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).todayComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = TodayPresenter(this)
        return binding.root
    }

    override fun showSomething() {
        TODO("Not yet implemented")
    }
}