package com.flexath.calculator.activities

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.flexath.calculator.R
import com.flexath.calculator.databinding.ActivityMainBinding
import com.flexath.calculator.library.CalculatorLibrary
import com.flexath.calculator.viewmodels.CalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var isEqualPressed = false
    private var isOperator = true
    private lateinit var resultString:String

    private var stack: Stack<String>? = null
    private var str:String? = String()

    private var calculator: CalculatorLibrary = CalculatorLibrary()

    @Inject
    lateinit var firstViewModelFactory:CalculatorViewModel.ViewModelFactory

    private val viewModelFirst: CalculatorViewModel by viewModels {
        CalculatorViewModel.providesFactory(assistedFactory = firstViewModelFactory, calculator = calculator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpThemeMode()
        setUpButtons()

        binding.tvResult.text = "0"
        binding.tvOperation.text = "0"
    }

    private fun changeStatusBar(color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
        binding.mainActivity.setBackgroundResource(color)
    }
    private fun setUpThemeMode() {
        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                changeStatusBar(R.color.colorLightMode)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.black))
                changeButtonToLightMode()
            } else {
                changeStatusBar(R.color.colorPrimary)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.white))
                changeButtonToDarkMode()
            }
        }
    }
    private fun setUpButtons() {
        binding.btnDot.setOnClickListener(this)
        binding.btnZero.setOnClickListener(this)
        binding.btnBackspace.setOnClickListener(this)
        binding.btnEqual.setOnClickListener(this)
        binding.btnOne.setOnClickListener(this)
        binding.btnTwo.setOnClickListener(this)
        binding.btnThree.setOnClickListener(this)
        binding.btnPlus.setOnClickListener(this)
        binding.btnFour.setOnClickListener(this)
        binding.btnFive.setOnClickListener(this)
        binding.btnSix.setOnClickListener(this)
        binding.btnMinus.setOnClickListener(this)
        binding.btnSeven.setOnClickListener(this)
        binding.btnEight.setOnClickListener(this)
        binding.btnNine.setOnClickListener(this)
        binding.btnMul.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
        binding.btnPlusOrMinus.setOnClickListener(this)
        binding.btnMod.setOnClickListener(this)
        binding.btnDiv.setOnClickListener(this)
    }
    private fun changeButtonToLightMode() {
        binding.btnDot.setImageResource(R.drawable.button_dot_light)
        binding.btnZero.setImageResource(R.drawable.button_zero_light)
        binding.btnBackspace.setImageResource(R.drawable.button_backspace_light)
        binding.btnOne.setImageResource(R.drawable.button_one_light)
        binding.btnTwo.setImageResource(R.drawable.button_two_light)
        binding.btnThree.setImageResource(R.drawable.button_three_light)
        binding.btnFour.setImageResource(R.drawable.button_four_light)
        binding.btnFive.setImageResource(R.drawable.button_five_light)
        binding.btnSix.setImageResource(R.drawable.button_six_light)
        binding.btnSeven.setImageResource(R.drawable.button_seven_light)
        binding.btnEight.setImageResource(R.drawable.button_eight_light)
        binding.btnNine.setImageResource(R.drawable.button_nine_light)
        binding.btnClear.setImageResource(R.drawable.button_clear_light)
        binding.btnPlusOrMinus.setImageResource(R.drawable.button_plus_minus_light)
        binding.btnMod.setImageResource(R.drawable.button_mod_light)
    }
    private fun changeButtonToDarkMode() {
        binding.btnDot.setImageResource(R.drawable.button_dot_dark)
        binding.btnZero.setImageResource(R.drawable.button_zero_dark)
        binding.btnBackspace.setImageResource(R.drawable.button_backspace_dark)
        binding.btnOne.setImageResource(R.drawable.button_one_dark)
        binding.btnTwo.setImageResource(R.drawable.button_two_dark)
        binding.btnThree.setImageResource(R.drawable.button_three_dark)
        binding.btnFour.setImageResource(R.drawable.button_four_dark)
        binding.btnFive.setImageResource(R.drawable.button_five_dark)
        binding.btnSix.setImageResource(R.drawable.button_six_dark)
        binding.btnSeven.setImageResource(R.drawable.button_seven_dark)
        binding.btnEight.setImageResource(R.drawable.button_eight_dark)
        binding.btnNine.setImageResource(R.drawable.button_nine_dark)
        binding.btnClear.setImageResource(R.drawable.button_clear_dark)
        binding.btnPlusOrMinus.setImageResource(R.drawable.button_plus_minus_dark)
        binding.btnMod.setImageResource(R.drawable.button_mod_dark)
    }

    private fun clickOnEqual() {

        if(this.str!!.last() in calculator.operators) {
            this.str = StringBuilder(this.str ?: "0").deleteCharAt(this.str!!.lastIndex).toString()
            binding.tvOperation.text = this.str
        }

        if(this.str!!.first() =='0') {
            this.str = StringBuilder(this.str ?: "0").deleteCharAt(0).toString()
            binding.tvOperation.text = this.str
        }

        if(this.str!!.isNotEmpty()) {

            stack = viewModelFirst.calculateString(this.str!!)
            //txtOperation.text = stack?.joinToString("","","")

            viewModelFirst.calculatedResult()
            resultString = stack?.peek().toString()
            var result = "= " + stack?.peek().toString()

            if(resultString.last() == '0' && resultString[resultString.lastIndex-1] == '.') {
                result = resultString.toDouble().toInt().toString()
                binding.tvResult.text = result
                resultString = result
                return
            }
            binding.tvResult.text =  result
        }
    }

    private fun operatorCorrection(operatorStr:String) {
        if(this.str!!.isNotEmpty()) {
            if(this.str!!.first() != '-' && this.str!!.first() in calculator.operators) {
                binding.tvOperation.text = "0"
            }
            else if(this.str!!.last() in calculator.operators) {
                this.str = StringBuilder(this.str.toString()).deleteCharAt(this.str!!.lastIndex).toString()
                binding.tvOperation.text = this.str
                binding.tvOperation.append(operatorStr)
            }else{
                binding.tvOperation.text = this.str
                binding.tvOperation.append(operatorStr)
            }
        }
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.btnZero -> {
                if(isEqualPressed) binding.tvOperation.text = "0"
                else binding.tvOperation.append("0")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnOne -> {
                if(isEqualPressed) binding.tvOperation.text = "1"
                else binding.tvOperation.append("1")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnTwo -> {
                if(isEqualPressed) binding.tvOperation.text = "2"
                else binding.tvOperation.append("2")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnThree -> {
                if(isEqualPressed) binding.tvOperation.text = "3"
                else binding.tvOperation.append("3")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnFour -> {
                if(isEqualPressed) binding.tvOperation.text = "4"
                else binding.tvOperation.append("4")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnFive -> {
                if(isEqualPressed) binding.tvOperation.text = "5"
                else binding.tvOperation.append("5")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnSix -> {
                if(isEqualPressed) binding.tvOperation.text = "6"
                else binding.tvOperation.append("6")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnSeven -> {
                if(isEqualPressed) binding.tvOperation.text = "7"
                else binding.tvOperation.append("7")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnEight -> {
                if(isEqualPressed) binding.tvOperation.text = "8"
                else binding.tvOperation.append("8")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }
            R.id.btnNine -> {
                if(isEqualPressed) binding.tvOperation.text = "9"
                else binding.tvOperation.append("9")
                this.str = binding.tvOperation.text.toString()
                isEqualPressed = false
                isOperator = false
                clickOnEqual()
            }

            R.id.btnPlusOrMinus -> binding.tvOperation.append("±")
            R.id.btnDot -> binding.tvOperation.append(".")
            R.id.btnEqual -> {
                isEqualPressed = true
                clickOnEqual()
            }

            R.id.btnPlus -> {
                isOperator = true
                if(isEqualPressed) {
                    binding.tvOperation.text = resultString
                    this.str = resultString
                }
                if(isOperator) {
                    operatorCorrection("+")
                }
                isEqualPressed = false
            }
            R.id.btnMinus -> {
                isOperator = true
                if(isEqualPressed) {
                    binding.tvOperation.text = resultString
                    this.str = resultString
                }
                if(isOperator) {
                    binding.tvOperation.text = this.str
                    binding.tvOperation.append("-")
                }
                isEqualPressed = false
            }
            R.id.btnMul -> {
                isOperator = true
                if(isEqualPressed) {
                    binding.tvOperation.text = resultString
                    this.str = resultString
                }
                if(isOperator) {
                    operatorCorrection("×")
                }
                isEqualPressed = false
            }
            R.id.btnDiv -> {
                isOperator = true
                if(isEqualPressed) {
                    binding.tvOperation.text = resultString
                    this.str = resultString
                }
                if(isOperator) {
                    operatorCorrection("÷")
                }
                isEqualPressed = false
            }
            R.id.btnMod -> {
                isOperator = true
                if(isEqualPressed) {
                    binding.tvOperation.text = resultString
                    this.str = resultString
                }
                if(isOperator) {
                    operatorCorrection("%")
                }
                isEqualPressed = false
            }
            R.id.btnBackspace -> {
                this.str = binding.tvOperation.text.toString()
                if(this.str!!.isNotEmpty()) {
                    this.str = StringBuilder(this.str.toString()).deleteCharAt(this.str!!.lastIndex).toString()
                    binding.tvOperation.text = this.str
                }
                isEqualPressed = false
                isOperator = false
            }
            R.id.btnClear -> {
                isEqualPressed = false
                this.str = ""
                binding.tvOperation.text = "0"
                binding.tvResult.text = "0"
            }
        }
    }
}