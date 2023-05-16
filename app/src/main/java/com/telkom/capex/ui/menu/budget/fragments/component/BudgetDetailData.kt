package com.telkom.capex.ui.menu.budget.fragments.component

import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentBudgetDetailRvBinding
import com.telkom.capex.databinding.ComponentBudgetEditBinding
import com.telkom.capex.databinding.FragmentBudgetDetailBinding
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.dashboard.helper.model.MonthlyBast
import dagger.hilt.android.AndroidEntryPoint
import java.time.Year


@AndroidEntryPoint
class BudgetDetailData: Fragment()  {

    private lateinit var binding: FragmentBudgetDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var rvBinding: ComponentBudgetDetailRvBinding
    lateinit var botDialog: ComponentBudgetEditBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val om = ObjectMapper()
            val raw = om.readValue(requireActivity().assets.readAssetFile(), MonthlyBast::class.java)

            budgetDetailRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                        rvBinding = ComponentBudgetDetailRvBinding.inflate(layoutInflater, parent, false)
                        return ViewHolder(rvBinding.root)
                    }

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val month = MonthModifier.getMonth( position + 1)
                        rvBinding.apply {
                            editTrigger.setOnClickListener {
                                botDialog = ComponentBudgetEditBinding.inflate(layoutInflater).apply {
                                    tvTitle.text = "$month, ${Year.now()}"
                                }
                                BottomSheetDialog(requireContext()).apply {
                                    setContentView(
                                        botDialog.root
                                    )
                                }.show()
                            }
                            budDetailRvMo.text = month

                            var string = raw.data?.get(position)?.duit.toString()
                            string = string.replace("Rp ", "Rp.")

                            budDetailRvVal.text = string

                            rvBudgetEditCompHidden.apply {
                                isScrollContainer = false
                                layoutManager = LinearLayoutManager(requireContext())
                                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                                    override fun onCreateViewHolder(
                                        parent: ViewGroup,
                                        viewType: Int
                                    ): ViewHolder =
                                        ViewHolder(
                                            LayoutInflater.from(parent.context)
                                                .inflate(R.layout.component_budget_edit_hidden, parent, false)
                                        )

                                    override fun onBindViewHolder(
                                        holder: ViewHolder,
                                        position: Int
                                    ) {
                                        val thisroot = holder.itemView

                                        thisroot.apply {
                                            when (position) {
                                                0 -> {
                                                    findViewById<TextView>(R.id.tv_hidden_name).text = "Plan RKAP"
                                                    findViewById<TextView>(R.id.tv_hidden_sum).text = "Rp.155.000.000.000"
                                                }

                                                else -> {

                                                    findViewById<TextView>(R.id.tv_hidden_name).text = "Plan PM"
                                                    findViewById<TextView>(R.id.tv_hidden_sum).text = "Rp.215.000.000.000"
                                                }
                                            }
                                        }
                                    }
                                    override fun getItemCount(): Int = 2
                                }
                            }
                        }
                    }

                    override fun getItemCount(): Int = 12
                }
            }
        }
    }

    private fun setSelectedItem(linout: LinearLayout) {

    }

    private fun AssetManager.readAssetFile(): String = open("month_money.json").bufferedReader().use { it.readText() }

}