package com.flexath.calculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexath.calculator.data.CalculatorRepository
import com.flexath.calculator.data.room.CalculatorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
    @Inject constructor(private val repository: CalculatorRepository) : ViewModel() {

    var getAllCalculations: LiveData<List<CalculatorEntity>> = repository.getAllCalculations

    fun insertCalculation(calculation: CalculatorEntity) = viewModelScope.launch {
        repository.insertCalculation(calculation)
    }

    fun deleteCalculation(id: Int) = viewModelScope.launch {
        repository.deleteCalculation(id)
    }
}