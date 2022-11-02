package com.telkom.capex.ui.budget.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.databind.ObjectMapper
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentBudgetDetailRvBinding
import com.telkom.capex.databinding.ComponentDashMonthBinding
import com.telkom.capex.databinding.FragmentBudgetDetailBinding
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.ui.budget.BudgetViewModel
import com.telkom.capex.ui.dashboard.DashboardViewModel
import com.telkom.capex.ui.dashboard.helper.adapter.DashboardDialogAdapter
import com.telkom.capex.ui.dashboard.helper.interfaces.DashboardInterface
import com.telkom.capex.ui.dashboard.helper.model.MonthlyBast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.floor

class BudgetData: Fragment()  {


    private lateinit var binding: FragmentBudgetDetailBinding
    private val viewModel by activityViewModels<BudgetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            val om = ObjectMapper()
            val raw = om.readValue(requireActivity().assets.readAssetFile(), MonthlyBast::class.java)

            budgetDetailRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(parent.context)
                                .inflate(R.layout.component_budget_detail_rv, parent, false)
                        )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val holderView = holder.itemView

                        holderView.apply {
                            findViewById<TextView>(R.id.bud_detail_rv_mo)
                                .text = MonthModifier.getMonth( position + 1)

                            var string = raw.data?.get(position)?.duit.toString()
                            string = string.replace("Rp ", "")

                            findViewById<TextView>(R.id.bud_detail_rv_val).text =
                                string
                        }
                    }

                    override fun getItemCount(): Int = 12
                }
            }
        }
    }

    class ViewHolder (private val view: View) : RecyclerView.ViewHolder(view)

    private fun AssetManager.readAssetFile(): String = open("month_money.json").bufferedReader().use { it.readText() }

}