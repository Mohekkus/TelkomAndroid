package com.telkom.capex.ui.menu.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetMonthlyBinding
import com.telkom.capex.etc.Utility
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailSmileResultItem
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BudgetMonthlyBAST: Fragment() {

    lateinit var binding: FragmentBudgetMonthlyBinding
    private val shared by hiltNavGraphViewModels<BudgetSharedViewModel>(R.id.mobile_navigation)
    @Inject lateinit var utility: Utility
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetMonthlyBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared.smile.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let {
                        val result = it?.result
                        result?.let { res ->
                            setSmile(res)
                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setSmile(res: List<BudgetDetailSmileResultItem>) {
        val apiData = res[0]
        binding.apply {
            budgetSmileValue.text = utility.money.format(apiData.totalsmile)
            budgetSmileRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(parent.context)
                                .inflate(R.layout.component_budget_edit_expanding, parent, false)
                        )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val root = holder.itemView
                        val data = mutableListOf("Preparing", "Delivering", "MOS", "UT", "BAST")

                        root.apply {
                            findViewById<TextView>(R.id.tv_budget_edit_comp_title).text = data[position]
                            findViewById<TextView>(R.id.tv_budget_edit_comp_sum).text =
                                utility.money.format(res[0].status?.get(position) ?: 0L)
                        }
                    }

                    override fun getItemCount(): Int = 4
                }
            }
        }
    }
}
