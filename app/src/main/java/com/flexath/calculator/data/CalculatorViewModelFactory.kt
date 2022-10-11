package com.flexath.calculator.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class CalculatorViewModelFactory(private val repository: CalculatorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CalculatorViewModel(repository) as T
    }
}