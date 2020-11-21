package com.example.innowise_test.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innowise_test.databinding.DayItemBinding
import com.example.innowise_test.model.weather.Day

class DayAdapter(
    private var data: List<Day> = listOf()
) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    private val recyclerViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = data[position]

        with(holder.binding) {
            recyclerSub.adapter = TimestampAdapter(day)
            recyclerSub.setRecycledViewPool(recyclerViewPool)
            this.day.text = day.timestamps[0].date
        }
    }

    override fun getItemCount() = data.size

    fun setData(days: List<Day>) {
        data = days
        notifyDataSetChanged()
    }

    fun getData() = data

    class ViewHolder(val binding: DayItemBinding) : RecyclerView.ViewHolder(binding.root)
}