package com.flexath.calculator.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexath.calculator.data.CalculatorRepository
import com.flexath.calculator.data.room.CalculatorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentViewModel
@Inject constructor(private val repository: CalculatorRepository) : ViewModel() {

    var calculatedResults:LiveData<List<CalculatorEntity>>? = null

    init {
        calculatedResults = repository.getAllCalculatedResults
    }

    fun addCalculatedResult(result: CalculatorEntity) = viewModelScope.launch {
        repository.addCalculatedResult(result)
    }

    fun deleteAllResults(resultList: List<CalculatorEntity>) = viewModelScope.launch {
        repository.deleteAllResults(resultList)
    }
}