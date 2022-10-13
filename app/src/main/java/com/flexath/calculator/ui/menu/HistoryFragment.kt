package com.flexath.calculator.ui.menu

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.calculator.R
import com.flexath.calculator.adapter.CalculatorHistoryAdapter
import com.flexath.calculator.data.room.CalculatorEntity
import com.flexath.calculator.data.viewmodels.HistoryFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*

@AndroidEntryPoint
class HistoryFragment : Fragment(),MenuProvider {

    private var adapter:CalculatorHistoryAdapter? = null
    private lateinit var viewModel:HistoryFragmentViewModel
    private var historyItemsList:List<CalculatorEntity>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val menuHost:MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = LinearLayoutManager(requireActivity())
        recyclerHistory.layoutManager = linearLayout
        recyclerHistory.setHasFixedSize(true)

        viewModel = ViewModelProvider(requireActivity())[HistoryFragmentViewModel::class.java]

        viewModel.calculatedResults?.observe(viewLifecycleOwner) {
            historyItemsList = it
            adapter = CalculatorHistoryAdapter(it)
            if(it.isNotEmpty()) historyCentreTxt.visibility = View.GONE
            recyclerHistory.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_history_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.deleteItem) {
            viewModel.deleteAllResults(historyItemsList!!)
        }
        return true
    }
}