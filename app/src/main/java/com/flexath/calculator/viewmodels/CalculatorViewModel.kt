package com.flexath.calculator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flexath.calculator.library.CalculatorLibrary
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.*

class CalculatorViewModel
@AssistedInject constructor(@Assisted private val calculator: CalculatorLibrary) : ViewModel() {

    private var stack: Stack<String>? = null
    private var result:Double = 0.0

    @AssistedFactory
    interface ViewModelFactory{
        fun create(calculator: CalculatorLibrary) : CalculatorViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        fun providesFactory(assistedFactory: ViewModelFactory, calculator: CalculatorLibrary)
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