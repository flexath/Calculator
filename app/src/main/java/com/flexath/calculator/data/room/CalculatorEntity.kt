package com.flexath.calculator.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculator_table")
data class CalculatorEntity(
    @ColumnInfo(name = "operation") val operation:String,
    @ColumnInfo(name = "result") val result:String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
