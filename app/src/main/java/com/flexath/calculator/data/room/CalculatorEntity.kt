package com.flexath.calculator.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculator_result")
data class CalculatorEntity(
    @ColumnInfo(name = "result") var result:String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}