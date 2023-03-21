package com.flexath.calculator.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.calculator.HistoryDelegate
import com.flexath.calculator.data.room.CalculatorEntity
import com.flexath.calculator.databinding.ViewHolderHistoryBinding
import com.flexath.calculator.viewholders.CalculatorViewHolder

class CalculatorAdapter(private val delegate: HistoryDelegate) : RecyclerView.Adapter<CalculatorViewHolder>() {

    private var mHistoryList:List<CalculatorEntity> = listOf()
    private var mTitleMode:String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        val view = ViewHolderHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalculatorViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        holder.bindHistoryData(mHistoryList[position],mTitleMode)
    }

    override fun getItemCount(): Int {
        return mHistoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHistoryData(historyList:List<CalculatorEntity>,titleMode:String) {
        mHistoryList = historyList
        mTitleMode = titleMode
        notifyDataSetChanged()
    }
}