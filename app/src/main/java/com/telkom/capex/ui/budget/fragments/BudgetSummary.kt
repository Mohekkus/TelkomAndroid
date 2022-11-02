package com.telkom.capex.ui.budget.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetSummaryBinding
import com.telkom.capex.etc.ChartDemo
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.ui.budget.BudgetViewModel
import com.telkom.capex.ui.dashboard.DashboardViewModel
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class BudgetSummary: Fragment() {

    private lateinit var binding: FragmentBudgetSummaryBinding
    private val viewModel by activityViewModels<BudgetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetSummaryBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            budgetSummaryChart.apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.apply {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
                loadDataWithBaseURL(
                    null,
                    ChartDemo.getPieBudget(),
                    "text/html",
                    "UTF-8",
                    null
                )
            }
            buttonBudgetViewList.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_budget_to_budgetlist)
            }
            budgetSummarySearch.apply {
                KeyboardVisibilityEvent.setEventListener(
                    requireActivity()
                ) {
                    if (!it) this.clearFocus()
                }

                setOnQueryTextListener(
                    object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            KeyboardUtils.hide(requireActivity(), binding.root)
                            budgetSummarySearch.clearFocus()

                            val submittedText = p0
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
                            return true
                        }
                    }
                )
            }

        }
    }
}