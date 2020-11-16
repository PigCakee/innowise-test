package com.example.innowise_test.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innowise_test.databinding.NumberBinding
import java.math.BigDecimal

class NumbersAdapter(
    private var data: List<BigDecimal> = listOf()
) : RecyclerView.Adapter<NumbersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            NumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = data[position]
        holder.binding.number.text = number.toString()
    }

    override fun getItemCount() = data.size

    fun setData(numbers: List<BigDecimal>) {
        data = numbers
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: NumberBinding) :
        RecyclerView.ViewHolder(binding.root)
}
