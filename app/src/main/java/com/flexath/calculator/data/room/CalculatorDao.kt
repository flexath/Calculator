package com.flexath.calculator.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(calculation:CalculatorEntity)

    @Query("DELETE FROM calculator_table WHERE id = :id")
    suspend fun deleteCalculation(id:Int)

    @Query("SELECT * FROM calculator_table ORDER BY id DESC")
    fun getAllCalculations(): LiveData<List<CalculatorEntity>>
}