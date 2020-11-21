package com.example.innowise_test.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innowise_test.databinding.TimestampBinding
import com.example.innowise_test.model.weather.Day

class TimestampAdapter(
    private var data: Day
) : RecyclerView.Adapter<TimestampAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TimestampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = data

        with(holder.binding) {
            temperature.text = day.timestamps[position].main.temp.toString()
            time.text = day.timestamps[position].date
            weather.text = day.timestamps[position].weather[0].description
            //skyImage.setImageDrawable()
        }
    }

    override fun getItemCount() = data.timestamps.size

    class ViewHolder(val binding: TimestampBinding) : RecyclerView.ViewHolder(binding.root)
}