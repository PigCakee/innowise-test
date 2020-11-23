package com.example.innowise_test.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innowise_test.databinding.DayItemBinding
import com.example.innowise_test.model.weather.Day
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DayAdapter(
    private var data: List<Day> = listOf(),
    private val context: Context
) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    private val recyclerViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = data[position]

        with(holder.binding) {
            recyclerSub.adapter = TimestampAdapter(day, context)
            recyclerSub.setRecycledViewPool(recyclerViewPool)
            val date = day.timestamps[0].date.split(' ').first()
            val currentDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Calendar.getInstance().time)

            val dayOfTheWeek = SimpleDateFormat("yyyy-MM-dd").parse(date)
                ?.let { SimpleDateFormat("EEEE").format(it) }

            if (date == currentDate) {
                this.day.text = "Today"
            } else {
                this.day.text = dayOfTheWeek
            }
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(val binding: DayItemBinding) : RecyclerView.ViewHolder(binding.root)
}