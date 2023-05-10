package com.telkom.capex.ui.menu.budget.fragments.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetMonthlyBinding
import com.telkom.capex.ui.menu.budget.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetMonthlyBAST: Fragment() {

    lateinit var binding: FragmentBudgetMonthlyBinding
    private val viewModel by activityViewModels<BudgetViewModel>()

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
        binding.apply {
            tvBudgetEditMonth.text = viewModel.month.value
            tvBudgetEditYear.setOnClickListener {
                Snackbar.make(root, "Show Year Picker", Snackbar.LENGTH_SHORT).show()
            }
            budgetEditRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(parent.context)
                                .inflate(R.layout.component_budget_edit_expanding, parent, false)
                        )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val root = holder.itemView
                        val data = mutableListOf<String>()
                        data.apply {
                            add("Preparing")
                            add("Delivering")
                            add("MOS")
                            add("UT")
                            add("BAST")
                        }

                        root.apply {
                            findViewById<TextView>(R.id.tv_budget_edit_comp_title).text = data[position]
                            findViewById<TextView>(R.id.tv_budget_edit_comp_sum).text = "Rp. 167.000.000.000"
//                            findViewById<RecyclerView>(R.id.rv_budget_edit_comp_hidden).apply {
//                                isScrollContainer = false
//                                layoutManager = LinearLayoutManager(requireContext())
//                                adapter = object : RecyclerView.Adapter<ViewHolder>() {
//                                    override fun onCreateViewHolder(
//                                        parent: ViewGroup,
//                                        viewType: Int
//                                    ): ViewHolder =
//                                        ViewHolder(
//                                            LayoutInflater.from(parent.context)
//                                                .inflate(R.layout.component_budget_edit_hidden, parent, false)
//                                        )
//
//                                    override fun onBindViewHolder(
//                                        holder: ViewHolder,
//                                        position: Int
//                                    ) {
//                                        val thisroot = holder.itemView
//
//                                        thisroot.apply {
//                                            findViewById<TextView>(R.id.tv_hidden_name).text = "Plan #${position + 1}"
//                                            findViewById<TextView>(R.id.tv_hidden_sum).text = "Rp.15.000.000.000"
//                                        }
//                                    }
//                                    override fun getItemCount(): Int = 2
//                                }
//                                setOnClickListener {
////                                    Snackbar.make(view, "Edit Value", Snackbar.LENGTH_SHORT).show()
//                                }
//                            }
//                            setOnClickListener {
//                                findViewById<LinearLayout>(R.id.budget_edit_comp_container_hidden)
//                                    .apply {
//                                        visibility = if (visibility == View.VISIBLE)
//                                            View.GONE
//                                        else
//                                            View.VISIBLE
//                                    }
//                            }
                        }
                    }

                    override fun getItemCount(): Int = 4
                }
            }
        }
    }
}


class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
