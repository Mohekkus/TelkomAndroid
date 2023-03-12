package com.telkom.capex.ui.menu.dashboard.helper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telkom.capex.ui.menu.dashboard.fragments.MonthlyBastFragment
import com.telkom.capex.ui.menu.dashboard.helper.model.DataItem

class DashboardByMonthAdapter(
    private val data: List<DataItem>,
    fragment: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment {
        return MonthlyBastFragment().apply {
            setData(data[position])
        }
    }
}