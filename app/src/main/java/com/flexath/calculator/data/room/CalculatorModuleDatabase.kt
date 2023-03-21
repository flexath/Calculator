package com.flexath.calculator.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalculatorModuleDatabase {

    @Provides
    @Singleton
    fun getCalculatorInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        CalculatorDatabase::class.java,
        "CalculatorDatabase"
    ).build()

    @Provides
    @Singleton
    fun dao(db:CalculatorDatabase) = db.getDao()
}