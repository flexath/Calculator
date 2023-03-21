package com.flexath.calculator.viewholders

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.flexath.calculator.HistoryDelegate
import com.flexath.calculator.R
import com.flexath.calculator.data.room.CalculatorEntity
import com.flexath.calculator.databinding.ViewHolderHistoryBinding

class CalculatorViewHolder(private val binding: ViewHolderHistoryBinding,
private val delegate: HistoryDelegate) : RecyclerView.ViewHolder(binding.root) {

    fun bindHistoryData(calculation: CalculatorEntity,titleMode:String) {
        val calculationStr = "${calculation.operation} = ${calculation.result}"
        binding.tvCalculation.text = calculationStr

        if(titleMode == "light") {
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorLightMode))
            binding.tvCalculation.setTextColor(binding.root.resources.getColor(R.color.colorPrimary,null))
        } else {
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
            binding.tvCalculation.setTextColor(binding.root.resources.getColor(R.color.colorLightMode,null))
        }

        binding.btnDelete.setOnClickListener {
            delegate.onTapDeleteButton(calculation.id)
        }

    }
}