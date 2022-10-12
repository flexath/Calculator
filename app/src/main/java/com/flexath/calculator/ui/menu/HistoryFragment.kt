package com.flexath.calculator.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.calculator.R
import com.flexath.calculator.adapter.CalculatorHistoryAdapter
import com.flexath.calculator.data.CalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var adapter:CalculatorHistoryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = LinearLayoutManager(requireActivity())
        recyclerHistory.layoutManager = linearLayout
        recyclerHistory.setHasFixedSize(true)

        val viewModel = ViewModelProvider(requireActivity())[CalculatorViewModel::class.java]

        viewModel.calculatedResults?.observe(viewLifecycleOwner) {
            adapter = CalculatorHistoryAdapter(it)
            if(it.isNotEmpty()) historyCentreTxt.visibility = View.GONE
            recyclerHistory.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }
}