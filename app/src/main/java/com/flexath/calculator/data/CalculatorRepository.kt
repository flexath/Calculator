package com.flexath.calculator.data

import com.flexath.calculator.data.room.CalculatorDao
import com.flexath.calculator.data.room.CalculatorEntity
import javax.inject.Inject

class CalculatorRepository
@Inject constructor(private val dao:CalculatorDao) {

    var getAllCalculatedResults = dao.getAllCalculatedResults()
    suspend fun addCalculatedResult(result:CalculatorEntity) = dao.addCalculatedResult(result)
}