package com.flexath.calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.calculator.R
import com.flexath.calculator.data.room.CalculatorEntity
import kotlinx.android.synthetic.main.history_list_item.view.*

class CalculatorHistoryAdapter(private val resultList:List<CalculatorEntity>) : RecyclerView.Adapter<CalculatorHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val items = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item,parent,false)
        return ViewHolder(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = resultList[position]
        holder.itemView.txtHistoryResult.text = item.result
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}