package com.flexath.calculator.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flexath.calculator.data.room.CalculatorEntity

@Dao
interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCalculatedResult(result:CalculatorEntity)

    @Delete
    suspend fun deleteAllResults(resultList:List<CalculatorEntity>)

    @Query("SELECT * FROM calculator_result ORDER BY id DESC")
    fun getAllCalculatedResults():LiveData<List<CalculatorEntity>>
}