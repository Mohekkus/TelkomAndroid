package com.telkom.capex.ui.menu.dashboard.helper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.FragmentBudgetBinding
import com.telkom.capex.etc.KeyboardUtils

class DashboardSearchFragment: Fragment() {

    private lateinit var binding: FragmentBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            budgetFilterContainer.visibility = View.GONE

            budgetSearch.apply {
                requestFocus()
                KeyboardUtils.show(requireActivity(), this)
            }

            budgetListBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}