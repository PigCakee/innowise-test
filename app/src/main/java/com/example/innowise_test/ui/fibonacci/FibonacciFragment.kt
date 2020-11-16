package com.example.innowise_test.ui.fibonacci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentFibonacciBinding
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.viewmodel.viewModel

class FibonacciFragment : Fragment() {
    private val binding by contentView<FragmentFibonacciBinding>(R.layout.fragment_fibonacci)
    private val model by viewModel<FibonacciViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.model = model
        return binding.root
    }
}