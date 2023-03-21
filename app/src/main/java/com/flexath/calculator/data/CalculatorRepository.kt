package com.flexath.calculator.data

import com.flexath.calculator.data.room.CalculatorDao
import com.flexath.calculator.data.room.CalculatorEntity
import javax.inject.Inject

class CalculatorRepository
@Inject constructor(private val dao:CalculatorDao) {

    var getAllCalculations = dao.getAllCalculations()
    suspend fun insertCalculation(calculation: CalculatorEntity) = dao.insertCalculation(calculation)
    suspend fun deleteCalculation(id:Int) = dao.deleteCalculation(id)
}