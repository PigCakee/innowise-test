package com.example.innowise_test.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innowise_test.databinding.TimestampBinding
import com.example.innowise_test.model.weather.Day
import com.example.innowise_test.model.weather.WeatherIconFactory

class TimestampAdapter(
    private var data: Day,
    private val context: Context
) : RecyclerView.Adapter<TimestampAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TimestampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = data

        with(holder.binding) {
            val temp = "${(day.timestamps[position].main.temp - 273).toInt()}°C"
            temperature.text = temp
            time.text = day.timestamps[position].date.split(' ').last().substring(0..4)
            var weath = day.timestamps[position].weather[0].description
            weath = weath.replaceFirst(weath[0], weath[0].toUpperCase())
            weather.text = weath
            val iconId = day.timestamps[position].weather.first().id
            skyImage.setImageDrawable(WeatherIconFactory.getIcon(iconId, context))
        }
    }

    override fun getItemCount() = data.timestamps.size

    class ViewHolder(val binding: TimestampBinding) : RecyclerView.ViewHolder(binding.root)
}