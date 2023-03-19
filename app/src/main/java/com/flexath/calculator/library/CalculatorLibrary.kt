package com.flexath.calculator.library

import java.util.*

class CalculatorLibrary {

    private var stack: Stack<String>? = null
    private var result = 0.0
    val operators = mutableListOf('+','-','×','÷','%')

    private fun add(firstNumber:Double,secondNumber:Double) : Double = firstNumber+secondNumber
    private fun subtract(firstNumber:Double,secondNumber:Double) : Double = firstNumber-secondNumber
    private fun multiply(firstNumber:Double,secondNumber:Double) : Double = firstNumber*secondNumber
    private fun divide(firstNumber:Double,secondNumber:Double) : Double = firstNumber/secondNumber
    private fun mod(firstNumber:Double,secondNumber:Double) : Double = firstNumber%secondNumber

    fun calculatedResult(): Double {

        if(stack?.size == 1) {
            return result
        }else{
            if(stack?.get(0).toString() == "-") {       // For only one first operator
                stack?.insertElementAt("0",0)
            }
            if(stack?.contains("×") == true || stack?.contains("÷") == true || stack?.contains("%") == true) {
                for(i in 1 until (stack?.size!!-1) step 2) {
                    if(stack!![i] == "×") {
                        result = multiply(stack!![i-1].toDouble(),stack!![i+1].toDouble())
                        stack?.add(i-1, result.toString())
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        return calculatedResult()
                    }
                    else if(stack!![i] == "÷"){
                        result = divide(stack!![i -1].toDouble(),stack!![i+1].toDouble())
                        stack?.add(i-1, result.toString())
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        return calculatedResult()
                    }
                    else if(stack!![i] == "%") {
                        result = mod(stack!![i -1].toDouble(),stack!![i+1].toDouble())
                        stack?.add(i-1, result.toString())
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        return calculatedResult()
                    }
                }
            }

            if(stack?.contains("+") == true || stack?.contains("-") == true) {
                for(i in 1 until (stack?.size!!-1) step 2) {
                    if(stack!![i] == "+") {
                        result = add(stack!![i-1].toDouble(),stack!![i+1].toDouble())
                        stack?.add(i-1, result.toString())
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        return calculatedResult()
                    }
                    else if(stack!![i] == "-"){
                        result = subtract(stack!![i -1].toDouble(),stack!![i+1].toDouble())
                        stack?.add(i-1, result.toString())
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        stack?.removeAt(i)
                        return calculatedResult()
                    }
                }
            }
            return calculatedResult()
        }
    }

    fun calculateString(str:String) : Stack<String> {
        stack = Stack<String>()
        var txt = String()
        var string = str

        string = removeFirstOperator(string)

        for (i in string.indices) {
            if (string[i] in operators) {
                if(txt != "") {
                    stack?.push(txt)
                    txt = ""
                }
                stack?.push(string[i].toString())
            }else{
                txt += string[i]
            }
        }
        stack?.push(txt)
        return stack!!
    }

    private fun removeFirstOperator(stringTxt:String) : String {
        var string = stringTxt
        if(string[0] == '-') {
            stack?.push(string[0].toString())
            string = (StringBuilder(string).deleteCharAt(0)).toString()
        }else if(string[0] != '-' && string[0] in operators) {
            string = (StringBuilder(string).deleteCharAt(0)).toString()
        }
        return string
    }
}
