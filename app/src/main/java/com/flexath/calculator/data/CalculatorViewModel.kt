package com.flexath.calculator.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexath.calculator.data.room.CalculatorEntity
import kotlinx.coroutines.launch

class CalculatorViewModel(private val repository: CalculatorRepository) : ViewModel() {

    var calculatedResults:LiveData<List<CalculatorEntity>>? = null

    init {
        calculatedResults = repository.getAllCalculatedResults
    }

    fun addCalculatedResult(result: CalculatorEntity) = viewModelScope.launch {
        repository.addCalculatedResult(result)
    }
}