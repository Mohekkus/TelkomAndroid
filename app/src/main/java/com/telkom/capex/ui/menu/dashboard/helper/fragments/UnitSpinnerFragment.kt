package com.telkom.capex.ui.menu.dashboard.helper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentDashUnitSpinnerBinding
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.dashboard.DashboardViewModel
import com.telkom.capex.ui.menu.search.model.SharedSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnitSpinnerFragment: Fragment() {

    private lateinit var binding: ComponentDashUnitSpinnerBinding
    private var viewID: Int? = null

    private val viewModel: DashboardViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ComponentDashUnitSpinnerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.division.observe(requireActivity()) { handler ->
            when (handler.status) {
                Status.SUCCESS -> {
                    handler.data.let { res ->
                        val list = mutableListOf<String>()
                        res?.result?.forEach {
                            list.add(it.strnamaorg)
                        }

                        binding.spinner.apply {
                            adapter = ArrayAdapter(requireContext(), R.layout.component_dash_spinner, list).apply {
                                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            }
                            onItemSelectedListener = object : OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapter: AdapterView<*>?,
                                    v: View?,
                                    pos: Int,
                                    id: Long
                                ) {
                                    viewModel.getPie(pos)
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {}
                            }
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
    }
}