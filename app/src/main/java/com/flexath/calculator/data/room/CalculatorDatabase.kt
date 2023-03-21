package com.flexath.calculator.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CalculatorEntity::class], version = 1 , exportSchema = false)
abstract class CalculatorDatabase : RoomDatabase() {

    abstract fun getDao():CalculatorDao

}