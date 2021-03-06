package com.example.innowise_test.ui.palindromes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.innowise_test.R
import com.example.innowise_test.databinding.FragmentPalindromesBinding
import com.example.innowise_test.ui.adapters.NumbersAdapter
import com.example.innowise_test.utils.inflaters.contentView
import com.example.innowise_test.utils.view.NUMBER_IS_TOO_BIG
import com.example.innowise_test.utils.viewmodel.viewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fibonacci.view.*

class PalindromesFragment : Fragment() {
    private val binding by contentView<FragmentPalindromesBinding>(R.layout.fragment_palindromes)
    private val model by viewModel<PalindromesViewModel>()
    private lateinit var adapter: NumbersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.model = model

        adapter = NumbersAdapter()
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val snackbar = Snackbar.make(binding.layout, "", Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorCarbonBlack
            )
        )

        binding.play.setOnClickListener {
            if (!binding.textInputLayout.number.text.isNullOrBlank()) {
                model.calculatePalindrome(binding.textInputLayout.number.text.toString().toInt())
            }
        }

        model.numbers.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        model.error.observe(this) {
            snackbar.setText(NUMBER_IS_TOO_BIG)
            snackbar.show()
        }
    }
}