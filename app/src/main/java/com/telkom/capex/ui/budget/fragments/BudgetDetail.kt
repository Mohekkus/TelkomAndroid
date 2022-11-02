package com.telkom.capex.ui.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentOverviewBinding
import com.telkom.capex.ui.budget.BudgetViewModel

class BudgetDetail: Fragment() {

    lateinit var binding: FragmentOverviewBinding
    private val viewModel by activityViewModels<BudgetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                            0 -> BudgetData()
                            else -> BudgetEdit()
                        }
                    }
                }
                viewModel.page.observe(viewLifecycleOwner) {
                    currentItem = if (it > 0) childCount
                    else 0
                }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        when (position) {
                            childCount ->
                                overviewEdit.setImageResource(R.drawable.ic_baseline_pending_actions_24)
                            else -> overviewEdit.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                        }
                    }
                })
                overviewEdit.setOnClickListener {
                    overviewPager.apply {
                        if (currentItem == childCount) {
                            currentItem = 0
                            overviewEdit.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                        }
                        else {
                            currentItem = childCount
                            overviewEdit.setImageResource(R.drawable.ic_baseline_pending_actions_24)
                        }
                    }
                }
            }
        }
    }
}