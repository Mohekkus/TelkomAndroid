package com.telkom.capex.ui.menu.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentOverviewBinding
import com.telkom.capex.ui.menu.budget.BudgetViewModel
import com.telkom.capex.ui.menu.budget.fragments.component.BudgetDetailData
import com.telkom.capex.ui.menu.budget.fragments.component.BudgetMonthlyBAST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetDetail: Fragment() {

    lateinit var binding: FragmentOverviewBinding
    private val viewModel by activityViewModels<BudgetViewModel>()

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
        binding.apply {
            overviewPager.apply {
                adapter = object : FragmentStateAdapter(this@BudgetDetail) {
                    override fun getItemCount(): Int = 2

                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> BudgetDetailData()
                            else -> BudgetMonthlyBAST()
                        }
                    }
                }
                viewModel.page.observe(viewLifecycleOwner) {
                    currentItem = if (it > 0) childCount
                    else 0
                }
                TabLayoutMediator(tabLayout, overviewPager) {tab, pos ->
                    tab.text = when (pos) {
                        0 -> "Monthly BAST"
                        1 -> "SMILE"
                        else -> null
                    }
                }.attach()
                overviewEdit.setOnClickListener {
                    findNavController().navigate(R.id.action_budgetlist_to_budgetEdit)
                }
            }
        }
    }
}