package com.example.innowise_test.ui.squares

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentSquaresBinding
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.viewmodel.viewModel

class SquaresFragment : Fragment() {
    private val binding by contentView<FragmentSquaresBinding>(R.layout.fragment_squares)
    private val model by viewModel<SquaresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.model = model
        return binding.root
    }
}