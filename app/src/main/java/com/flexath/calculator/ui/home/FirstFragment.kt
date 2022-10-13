package com.flexath.calculator.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.flexath.calculator.R
import com.flexath.calculator.data.viewmodels.HistoryFragmentViewModel
import com.flexath.calculator.data.room.CalculatorEntity
import com.flexath.calculator.data.viewmodels.FirstFragmentViewModel
//import com.flexath.calculator.data.viewmodels.FirstFragmentViewModelFactory
import com.flexath.calculator.library.Calculator
import com.flexath.calculator.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment(),View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var viewModelHistory: HistoryFragmentViewModel
    private var isEqualPressed = false
    private var isOperator = true
    private var result = 0.0

    private var stack:Stack<String>? = null
    private var str:String? = String()

    private var calculator: Calculator = Calculator()

    @Inject
    lateinit var firstViewModelFactory:FirstFragmentViewModel.ViewModelFactory

    private val viewModelFirst: FirstFragmentViewModel by viewModels {
        FirstFragmentViewModel.providesFactory(assistedFactory = firstViewModelFactory, calculator = calculator)
    }

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

        viewModelHistory = ViewModelProvider(requireActivity())[HistoryFragmentViewModel::class.java]
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

    private fun clickOnEqual() {

        if(this.str!!.isNotEmpty()) {

            stack = viewModelFirst.calculateString(this.str!!)
            txtOperation.text = stack?.joinToString("","","")

            viewModelFirst.calculatedResult()
            val resultString = "= " + stack?.peek().toString()
            for(i in stack!!.elements()) Log.i("StringJoin",i)

            txtResult.text =  resultString

            txtResult.textSize = 40.0f
            txtResult.typeface = Typeface.DEFAULT_BOLD

            txtOperation.textSize = 25.0f
            txtOperation.typeface = Typeface.DEFAULT

            val resultAndOperation = this.str + txtResult.text.toString()
            val resultStr = CalculatorEntity(resultAndOperation)
            viewModelHistory.addCalculatedResult(resultStr)
        }
    }

    override fun onClick(v: View?) {
        isOperator = true

        txtOperation.textSize = 40.0f
        txtOperation.typeface = Typeface.DEFAULT_BOLD

        txtResult.textSize = 25.0f
        txtResult.typeface = Typeface.DEFAULT

        when(v?.id) {
            R.id.btnZero -> {
                if(isEqualPressed) txtOperation.text = "0"
                else txtOperation.append("0")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnOne -> {
                if(isEqualPressed) txtOperation.text = "1"
                else txtOperation.append("1")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnTwo -> {
                if(isEqualPressed) txtOperation.text = "2"
                else txtOperation.append("2")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnThree -> {
                if(isEqualPressed) txtOperation.text = "3"
                else txtOperation.append("3")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnFour -> {
                if(isEqualPressed) txtOperation.text = "4"
                else txtOperation.append("4")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnFive -> {
                if(isEqualPressed) txtOperation.text = "5"
                else txtOperation.append("5")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnSix -> {
                if(isEqualPressed) txtOperation.text = "6"
                else txtOperation.append("6")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnSeven -> {
                if(isEqualPressed) txtOperation.text = "7"
                else txtOperation.append("7")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnEight -> {
                if(isEqualPressed) txtOperation.text = "8"
                else txtOperation.append("8")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnNine -> {
                if(isEqualPressed) txtOperation.text = "9"
                else txtOperation.append("9")
                this.str = txtOperation.text.toString()
                isEqualPressed = false
                isOperator = false
            }

            R.id.btnBox -> {}
            R.id.btnDot -> txtOperation.append(".")
            R.id.btnEqual -> {
                isEqualPressed = true
                clickOnEqual()
            }

            R.id.btnPlus -> {
                if(isOperator) {
                    txtOperation.text = this.str
                    txtOperation.append("+")
                }
                isEqualPressed = false
            }
            R.id.btnMinus -> {
                if(isOperator) {
                    txtOperation.text = this.str
                    txtOperation.append("-")
                }
                isEqualPressed = false
            }
            R.id.btnCross -> {
                if(isOperator) {
                    txtOperation.text = this.str
                    txtOperation.append("⨯")
                }
                isEqualPressed = false
            }
            R.id.btnDivide -> {
                if(isOperator) {
                    txtOperation.text = this.str
                    txtOperation.append("÷")
                }
                isEqualPressed = false
            }
            R.id.btnPercent -> {
                if(isOperator) {
                    txtOperation.text = this.str
                    txtOperation.append("%")
                }
                isEqualPressed = false
            }
            R.id.btnBackSpace -> {
                this.str = txtOperation.text.toString()
                if(this.str!!.isNotEmpty()) {
                    this.str = StringBuilder(this.str.toString()).deleteCharAt(this.str!!.lastIndex).toString()
                    txtOperation.text = this.str
                }
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnClear -> {
                this.str = ""
                txtOperation.text = ""
                txtResult.text = "0"
                txtResult.textSize = 35.0f
                txtResult.typeface = Typeface.DEFAULT_BOLD
            }
        }
    }
}