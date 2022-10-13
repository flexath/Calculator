package com.flexath.calculator.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flexath.calculator.library.Calculator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.*

class FirstFragmentViewModel
@AssistedInject constructor(@Assisted private val calculator: Calculator) : ViewModel() {

    private var stack:Stack<String>? = null
    private var result:Double = 0.0

    @AssistedFactory
    interface ViewModelFactory{
        fun create(calculator: Calculator) : FirstFragmentViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        fun providesFactory(assistedFactory: ViewModelFactory, calculator: Calculator)
            : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(calculator) as T
                }
            }
    }

    fun calculateString(string:String) : Stack<String> {
        viewModelScope.launch {
            stack = calculator.calculateString(string)
        }
        return stack!!
    }

    fun calculatedResult() : Double {
        viewModelScope.launch {
            result = calculator.calculatedResult()
        }
        return result
    }
}