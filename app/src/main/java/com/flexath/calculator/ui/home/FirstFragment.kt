package com.flexath.calculator.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.flexath.calculator.R
import com.flexath.calculator.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

class FirstFragment : Fragment(),View.OnClickListener {

    private lateinit var navController: NavController
    private var stack:Stack<String>? = null
    private var str = String()
    private var result = 0.0
    private val operators = mutableListOf<Any>('+','-','x','÷','%')

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = (activity as MainActivity).navController
        toolBarSetup()
        onClickSetup()

        txtResult.text = result.toString()
    }

    private fun toolBarSetup() {
        toolbar.inflateMenu(R.menu.menu_item)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuHistory -> {
                    Toast.makeText(requireContext(), "History menu is clicked", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.first_history_action)
                }

                R.id.menuSetting -> {
                    Toast.makeText(requireContext(), "Setting menu is clicked", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.first_setting_action)
                }
            }
            true
        }
    }

    private fun add(firstNumber:Double,secondNumber:Double) : Double = firstNumber+secondNumber
    private fun subtract(firstNumber:Double,secondNumber:Double) : Double = firstNumber-secondNumber
    private fun multiply(firstNumber:Double,secondNumber:Double) : Double = firstNumber*secondNumber
    private fun divide(firstNumber:Double,secondNumber:Double) : Double = firstNumber/secondNumber
    private fun mod(firstNumber:Double,secondNumber:Double) : Double = firstNumber%secondNumber

    private fun calculatedResult() : Double {

        if(stack?.size == 1) {
            return result
        }
        else {
            if(stack!![1] == "+") {
                result = add(stack!![0].toDouble(),stack!![2].toDouble())
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.add(0, result.toString())
                return calculatedResult()
            }
            else if(stack!![1] == "-") {
                result = subtract(stack!![0].toDouble(),stack!![2].toDouble())
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.add(0, result.toString())
                return calculatedResult()
            }
            else if(stack!![1] == "x") {
                result = multiply(stack!![0].toDouble(),stack!![2].toDouble())
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.add(0, result.toString())
                return calculatedResult()
            }
            else if(stack!![1] == "÷") {
                result = divide(stack!![0].toDouble(),stack!![2].toDouble())
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.add(0, result.toString())
                return calculatedResult()
            }
            else if(stack!![1] == "%") {
                result = mod(stack!![0].toDouble(),stack!![2].toDouble())
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.removeFirst()
                stack?.add(0, result.toString())
                return calculatedResult()
            }else{
                return result
            }
        }
    }

    private fun calculateString(str:String) : Stack<String> {
        stack = Stack<String>()
        var txt = String()

        for(i in str.indices) {
            if(str[i] in operators) {
                if(str[i] in operators && str[i-1] in operators) {
                    stack?.pop()
                    stack?.push(str[i].toString())
                    txtOperation.text = StringBuilder(str).deleteCharAt(i-1)
                }
                else if(str[i] in operators) {
                    stack?.push(txt)
                    stack?.push(str[i].toString())
                    txt = ""
                } else{
                    stack?.push(txt)
                    txt = ""
                }
            }
            else{
                txt += str[i]
            }
        }
        stack?.push(txt)
        return stack!!
    }

    private fun onClickSetup() {
        btnZero.setOnClickListener(this)
        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnFive.setOnClickListener(this)
        btnSix.setOnClickListener(this)
        btnSeven.setOnClickListener(this)
        btnEight.setOnClickListener(this)
        btnNine.setOnClickListener(this)
        btnBox.setOnClickListener(this)
        btnDot.setOnClickListener(this)
        btnEqual.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
        btnMinus.setOnClickListener(this)
        btnCross.setOnClickListener(this)
        btnDivide.setOnClickListener(this)
        btnPercent.setOnClickListener(this)
        btnBackSpace.setOnClickListener(this)
        btnClear.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        this.str = txtOperation.text.toString()

        when(v?.id) {
            R.id.btnZero -> txtOperation.append("0")
            R.id.btnOne -> txtOperation.append("1")
            R.id.btnTwo -> txtOperation.append("2")
            R.id.btnThree -> txtOperation.append("3")
            R.id.btnFour -> txtOperation.append("4")
            R.id.btnFive -> txtOperation.append("5")
            R.id.btnSix -> txtOperation.append("6")
            R.id.btnSeven -> txtOperation.append("7")
            R.id.btnEight -> txtOperation.append("8")
            R.id.btnNine -> txtOperation.append("9")
            R.id.btnBox -> {}
            R.id.btnDot -> txtOperation.append(".")

            R.id.btnEqual -> {
                while(this.str.last() in operators) {
                    this.str = StringBuilder(this.str).also{
                        it.deleteCharAt(this.str.lastIndex)
                    }.toString()
                }
                stack = calculateString(this.str)
                txtResult.text =  "= " + stack?.let { calculatedResult().toString() }
            }

            R.id.btnPlus -> txtOperation.append("+")
            R.id.btnMinus -> txtOperation.append("-")
            R.id.btnCross -> txtOperation.append("x")
            R.id.btnDivide -> txtOperation.append("÷")
            R.id.btnPercent -> txtOperation.append("%")
            R.id.btnBackSpace -> txtOperation.text = StringBuilder(this.str).deleteCharAt(this.str.lastIndex)
            R.id.btnClear -> txtOperation.text = ""
        }
    }
}