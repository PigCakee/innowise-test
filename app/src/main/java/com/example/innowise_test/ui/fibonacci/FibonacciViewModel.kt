package com.example.innowise_test.ui.fibonacci

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adverboard.utils.livedata.SingleLiveEvent
import com.example.innowise_test.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FibonacciViewModel : ViewModel() {
    val numbers: MutableLiveData<List<BigDecimal>> =
        mutableLiveData()

    val isLoading: ObservableField<Int> = ObservableField(View.INVISIBLE)

    val error: SingleLiveEvent<Void> =
        SingleLiveEvent()

    fun calculateFibonacci(number: Int) = viewModelScope.launch(Dispatchers.Default) {
        isLoading.set(View.VISIBLE)

        val numList: MutableList<BigDecimal> = mutableListOf(1.toBigDecimal())
        var num1: BigDecimal = 0.toBigDecimal()
        var num2: BigDecimal = 1.toBigDecimal()

        try {
            for (i in 1..number) {
                val sum = num1 + num2
                num1 = num2
                num2 = sum

                val square: BigDecimal = (sum * sum)
                numList.add(square)
            }
        } catch (e: Exception) {
            error.call()
        } finally {
            numbers.postValue(numList)
            isLoading.set(View.INVISIBLE)
        }
    }
}
