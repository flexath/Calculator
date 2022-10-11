package com.flexath.calculator.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.flexath.calculator.R
import com.flexath.calculator.data.CalculatorRepository
import com.flexath.calculator.data.CalculatorViewModel
import com.flexath.calculator.data.CalculatorViewModelFactory
import com.flexath.calculator.data.room.CalculatorDatabase
import com.flexath.calculator.data.room.CalculatorEntity
import com.flexath.calculator.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

class FirstFragment : Fragment(),View.OnClickListener {

    private lateinit var navController: NavController
    private var stack:Stack<String>? = null
    private var str:String? = String()
    private var result = 0.0
    private val operators = mutableListOf<Any>('+','-','⨯','÷','%')
    private lateinit var viewModel:CalculatorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = (activity as MainActivity).navController
        toolBarSetup()
        onClickSetup()

        txtResult.text = result.toString()
        txtOperation.text = this.str

        val dao = CalculatorDatabase.getCalculatorInstance(requireActivity()).dao
        val repository = CalculatorRepository(dao)
        val factory = CalculatorViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(),factory)[CalculatorViewModel::class.java]
    }

    private fun toolBarSetup() {
        toolbar.inflateMenu(R.menu.menu_item)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuHistory -> {
                    navController.navigate(R.id.first_history_action)
                }

                R.id.menuSetting -> {
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

    private fun calculateString(string:String) : Stack<String> {
        stack = Stack<String>()
        var txt = String()
        var str = string

        while(str[0] in operators) {      // For first consecutive operators
            if(str[0] == '-' && str[1] !in operators) {
                stack?.push(str[0].toString())
                break
            }else{
                str = StringBuilder(str).also {
                    it.deleteCharAt(0)
                }.toString()
            }
        }

        txtOperation.text = str

        for(i in str.indices) {

            if(str[i] in operators) {
                if(str[i] in operators && i == 0){
                    continue
                }
                else if(str[i] in operators && str[i-1] in operators) {  // For consecutive operators
                    stack?.pop()
                    stack?.push(str[i].toString())
                    txtOperation.text = StringBuilder(str).deleteCharAt(i-1)
                }
                else if(str[i] in operators) { // For only one operator in middle
                    stack?.push(txt)
                    stack?.push(str[i].toString())
                    txt = ""
                }else{
                    stack?.push(txt)
                    txt = ""
                }
            }
            else{
                txt += str[i]
            }
        }
        stack?.push(txt)
        txtOperation.text = stack?.joinToString("","","")
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
        txtOperation.text = this.str

        txtOperation.textSize = 30.0f
        txtOperation.typeface = Typeface.DEFAULT_BOLD

        txtResult.textSize = 25.0f
        txtResult.typeface = Typeface.DEFAULT

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
                if(this.str!!.isNotEmpty()) {
                    while(this.str!!.last() in operators) {
                        this.str = StringBuilder(this.str.toString()).also{
                            it.deleteCharAt(this.str!!.lastIndex)
                        }.toString()
                    }
                    stack = calculateString(this.str!!)
                    val resultString = "= " + stack?.let { calculatedResult().toString() }
                    txtResult.text =  resultString
                    txtResult.textSize = 35.0f
                    txtResult.typeface = Typeface.DEFAULT_BOLD
                    txtOperation.textSize = 20.0f
                    txtOperation.typeface = Typeface.DEFAULT

                    val resultAndOperation = this.str + txtResult.text.toString()
                    val resultStr = CalculatorEntity(resultAndOperation)
                    viewModel.addCalculatedResult(resultStr)
                }
            }

            R.id.btnPlus -> txtOperation.append("+")
            R.id.btnMinus -> txtOperation.append("-")
            R.id.btnCross -> txtOperation.append("⨯")
            R.id.btnDivide -> txtOperation.append("÷")
            R.id.btnPercent -> txtOperation.append("%")
            R.id.btnBackSpace -> {
                if(this.str!!.isNotEmpty()) {
                    txtOperation.text = StringBuilder(this.str.toString()).deleteCharAt(this.str!!.lastIndex)
                }
            }
            R.id.btnClear -> txtOperation.text = ""
        }
    }
}