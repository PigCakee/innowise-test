package com.example.innowise_test.ui.palindromes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentPalindromesBinding
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.viewmodel.viewModel

class PalindromesFragment : Fragment() {
    private val binding by contentView<FragmentPalindromesBinding>(R.layout.fragment_palindromes)
    private val model by viewModel<PalindromesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.model = model
        return binding.root
    }
}