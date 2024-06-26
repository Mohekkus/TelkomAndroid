package com.telkom.capex.ui.menu.budget.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentBudgetSettingBinding
import com.telkom.capex.databinding.FragmentOverviewBinding
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.etc.Utility
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailMonthlyResultItem
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailResultItem
import com.telkom.capex.ui.menu.budget.helper.model.MonthlyDataItem
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BudgetDetail: Fragment() {

    lateinit var binding: FragmentOverviewBinding
    private val shared by hiltNavGraphViewModels<BudgetSharedViewModel>(R.id.mobile_navigation)
    @Inject lateinit var utility: Utility

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared.apply {
            detail.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let {
                            val result = it?.result
                            result?.let { res ->
                                assembleData(res[0])
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
            monthlyData.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let {
                            val result = it?.result
                            result?.let { res ->
                                val data = res[0]
                                    .mdata
                                    ?.get(shared.monthList.value ?: MonthModifier.currentMonthInt())
                                    ?: return@observe

                                setHighlightedData(data)
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
            highlightedData.observe(requireActivity()) {
                setMonthValue(it)
            }
        }
        binding.apply {
            budgetPager.apply {
                adapter = object : FragmentStateAdapter(this@BudgetDetail) {
                    override fun getItemCount(): Int = 2

                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> BudgetDetailData()
                            else -> BudgetMonthlyBAST()
                        }
                    }
                }
                TabLayoutMediator(budgetTabLayout, budgetPager) {tab, pos ->
                    tab.text = when (pos) {
                        0 -> "Detail Kontrak"
                        1 -> "SMILE"
                        else -> null
                    }
                }.attach()
                budgetSettingEdit.setOnClickListener {
                    shared.apply {
                        if (editting.value == true) {
                            AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme).apply {
                                setTitle("Saving Changes")
                                setMessage("Are you sure to make this changes?")
                                setPositiveButton("Yes, I do") { dialogInterface, i ->
                                    appliedChanges()
                                    setEditting(false)
                                }
                                setNegativeButton("Cancel") { _, _ ->
                                    revertChanges()
                                    setEditting(false)
                                }
                                setOnDismissListener {
                                    editting.observe(requireActivity()) {
                                        if (!it) {
                                            budgetSettingEdit.setImageResource(R.drawable.baseline_menu_24)
                                        }
                                    }
                                }
                            }
                                .create()
                                .also {
                                    it.apply {
                                        window?.setBackgroundDrawableResource(R.drawable.drawable_rounded)
                                    }
                                }
                                .show()
                        } else {
                            val viewdialog = ComponentBudgetSettingBinding.inflate(layoutInflater)
                            AlertDialog.Builder(requireActivity()).apply {
                                setting.observe(requireActivity()) {
                                    viewdialog.budgetBudgetSettingEdit.visibility =
                                        if (it) View.GONE
                                        else View.VISIBLE
                                }
                                setView(viewdialog.root)
                            }
                                .create()
                                .also { dialog ->
                                    dialog.apply {
                                        window?.setBackgroundDrawableResource(R.color.transparent)
                                        setOnDismissListener {
                                            budgetSettingEdit.setImageResource(R.drawable.baseline_done_24)
                                        }
                                    }
                                    viewdialog.apply {
                                        budgetBudgetSettingEdit.setOnClickListener {
                                            setEditting(true)
                                            dialog.dismiss()
                                        }
//                                        buttonBudgetSettingContractView.setOnClickListener {
//                                            dialog.dismiss()
//                                        }
                                    }
                                }
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setMonthValue(data: MonthlyDataItem?) {
        if (data == null) return
        binding.apply {
            shared.apply {
                val month = MonthModifier.getMonth(detailMonth.value ?: 0)
                budgetDate.text = "$month, ${year.value.toString()}"
            }

            utility.money.apply {
                data.apply {

                    budgetActual.text = format(actual)
                    budgetPlanRkap.text = format(planningRKAP)
                    budgetPlanPm.text = format(planningPM)

                    if (planningPM < planningRKAP)
                        progressDetailPm.bringToFront()
                    else
                        progressDetailRkap.bringToFront()

                    progressDetailPm.progress = percentage(actual, planningPM)
                    progressDetailRkap.progress = percentage(actual, planningRKAP)
                    budgetDetailTxtRkapWithPercentage.apply {
                        if (text.toString().contains('('))
                            text = text
                                .replace(
                                    Regex("\\(.*\\)"),
                                    "(${percentage(actual,planningRKAP)}%)"
                                )
                        else
                            append(
                                " (${percentage(actual,planningRKAP)}%)"
                            )
                    }
                    budgetDetailTxtPmWithPercentage.apply {
                        if (text.toString().contains('('))
                            text = text
                                .replace(
                                    Regex("\\(.*\\)"),
                                    "(${percentage(actual,planningPM)}%)"
                                )
                        else
                            append(
                                " (${percentage(actual,planningPM)}%)"
                            )
                    }
                }
            }
        }
    }

    private fun assembleData(res: BudgetDetailResultItem) {
        binding.apply {
            res.apply {
                budgetTitle.text = "Budget Detail"
                budgetStatus.text =
                    when (boolstatus) {
                        false -> "Inactive"
                        else -> "Active"
                    }
                budgetContractsName.text = strnamakontrak
                budgetContractsDescription.text = txtdetailproyek
                budgetContractsUnit.text = strnamaorg
                budgetContractsMitra.text = strnamamitra
                budgetContractsEdc.text = dtedc.split("T")[0]
                budgetContractsToc.text = dttoc.split("T")[0]
            }
        }
        shared.apply {
            getSmile()
        }
    }
}