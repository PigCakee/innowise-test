package com.example.innowise_test.ui.squares

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

class SquaresViewModel : ViewModel() {
    val numbers: MutableLiveData<List<BigDecimal>> =
        mutableLiveData()

    val isLoading: ObservableField<Int> = ObservableField(View.INVISIBLE)

    val error: SingleLiveEvent<Void> =
        SingleLiveEvent()

    fun calculateSquares(number: Int) = viewModelScope.launch(Dispatchers.Default) {
        isLoading.set(View.VISIBLE)

        val numList: MutableList<BigDecimal> = mutableListOf()

        try {
            for (i in 1..number) {
                if(i % 2 == 1) {
                    numList.add((i * i).toBigDecimal())
                }
            }
        } catch (e: Exception) {
            error.postValue(null)
        } finally {
            numbers.postValue(numList)
            isLoading.set(View.INVISIBLE)
        }
    }
}
