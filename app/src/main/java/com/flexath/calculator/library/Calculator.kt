package com.flexath.calculator.library

import java.util.*

class Calculator {

    private var stack: Stack<String>? = null
    private var result = 0.0
    val operators = mutableListOf('+','-','⨯','÷','%')

    private fun add(firstNumber:Double,secondNumber:Double) : Double = firstNumber+secondNumber
    private fun subtract(firstNumber:Double,secondNumber:Double) : Double = firstNumber-secondNumber
    private fun multiply(firstNumber:Double,secondNumber:Double) : Double = firstNumber*secondNumber
    private fun divide(firstNumber:Double,secondNumber:Double) : Double = firstNumber/secondNumber
    private fun mod(firstNumber:Double,secondNumber:Double) : Double = firstNumber%secondNumber

    fun calculatedResult() : Double {

        if(stack?.get(0).toString() == "-") {       // For only one first operator
            stack?.insertElementAt("0",0)
        }

        if(stack?.size == 1 || stack?.size == 0) {
            return result
        }else{
            if(stack!![1] == "+") {
                result = add(stack!![0].toDouble(),stack!![2].toDouble())
            }
            else if(stack!![1] == "-") {
                result = subtract(stack!![0].toDouble(),stack!![2].toDouble())
            }
            else if(stack!![1] == "⨯") {
                result = multiply(stack!![0].toDouble(),stack!![2].toDouble())
            }
            else if(stack!![1] == "÷") {
                result = divide(stack!![0].toDouble(),stack!![2].toDouble())
            }
            else if(stack!![1] == "%") {
                result = mod(stack!![0].toDouble(),stack!![2].toDouble())
            }
            stack?.removeFirst()
            stack?.removeFirst()
            stack?.removeFirst()
            stack?.add(0, result.toString())
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