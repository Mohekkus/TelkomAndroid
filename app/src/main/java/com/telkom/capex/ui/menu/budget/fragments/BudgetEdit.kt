package com.telkom.capex.ui.menu.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.FragmentBudgetEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetEdit: Fragment() {

    lateinit var binding: FragmentBudgetEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}