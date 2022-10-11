package com.flexath.calculator.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flexath.calculator.data.room.CalculatorEntity

@Dao
interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCalculatedResult(result:CalculatorEntity)

    @Query("SELECT * FROM calculator_result ORDER BY id DESC")
    fun getAllCalculatedResults():LiveData<List<CalculatorEntity>>
}