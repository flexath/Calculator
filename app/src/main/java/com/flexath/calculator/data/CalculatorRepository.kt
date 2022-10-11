package com.flexath.calculator.data

import com.flexath.calculator.data.room.CalculatorDao
import com.flexath.calculator.data.room.CalculatorEntity

class CalculatorRepository(private val dao:CalculatorDao) {

    var getAllCalculatedResults = dao.getAllCalculatedResults()
    suspend fun addCalculatedResult(result:CalculatorEntity) = dao.addCalculatedResult(result)
}