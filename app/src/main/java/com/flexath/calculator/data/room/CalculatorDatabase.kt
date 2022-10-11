package com.flexath.calculator.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalculatorEntity::class], version = 1)
abstract class CalculatorDatabase : RoomDatabase() {

    abstract val dao:CalculatorDao

    companion object {
        @Volatile
        private var INSTANCE:CalculatorDatabase? = null

        fun getCalculatorInstance(context: Context) = INSTANCE ?: kotlin.synchronized(this){
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                CalculatorDatabase::class.java,
                "CalculatorDatabase"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}