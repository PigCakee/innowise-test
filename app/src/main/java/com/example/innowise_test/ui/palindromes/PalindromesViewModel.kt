package com.example.innowise_test.ui.palindromes

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

class PalindromesViewModel : ViewModel() {
    val numbers: MutableLiveData<List<BigDecimal>> =
        mutableLiveData()

    val isLoading: ObservableField<Int> = ObservableField(View.INVISIBLE)

    val error: SingleLiveEvent<Void> =
        SingleLiveEvent()

    fun calculatePalindrome(number: Int) = viewModelScope.launch(Dispatchers.Default) {
        isLoading.set(View.VISIBLE)

        val numList: MutableList<BigDecimal> = mutableListOf()

        try {
            for (i in 1..number) {
                var reversedInteger = 0
                var remainder: Int
                val originalInteger: Int = i

                var temp = i
                while (temp != 0) {
                    remainder = temp % 10
                    reversedInteger = reversedInteger * 10 + remainder
                    temp /= 10
                }

                if (originalInteger == reversedInteger) {
                    numList.add((originalInteger).toBigDecimal())
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
