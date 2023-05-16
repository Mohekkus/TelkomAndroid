package com.telkom.capex.ui.menu.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.LayoutSearchContractBinding
import com.telkom.capex.etc.ToMiddleScroller
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.search.model.ResultItem
import com.telkom.capex.ui.menu.search.model.SharedSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSearchContract: Fragment() {

    lateinit var binding: LayoutSearchContractBinding
    private val viewModel: FragmentSearchViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private val shared: SharedSearchViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSearchContractBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { response ->
                        val result = response?.result
                        if (result != null) {
                            setRecyclerView(result)
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

        binding.apply {
            tieNomorKontrak.doOnTextChanged { text, start, before, count ->
                viewModel.setQuery(text.toString())
            }

            button3.setOnClickListener {
                viewModel.apply {
                    if (viewModel.getQuery().value.isNullOrEmpty())
                        return@setOnClickListener

                    postQuery()
                }
            }
        }
    }

    private fun setRecyclerView(result: List<ResultItem>) {
        binding.rvContractList.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            adapter = object : RecyclerView.Adapter<ViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                    ViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.component_budget_item, parent, false)
                    )

                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    val itemView = holder.itemView

                    itemView.setOnClickListener {
                        when (shared.getFlag().value) {
                            SharedSearchViewModel.DICTIONARY.CONTRACT -> {}
                            SharedSearchViewModel.DICTIONARY.BUDGET -> {}
                            else -> {}
                        }
                    }

                }
                override fun getItemCount(): Int = result.size
            }
        }
        removeLoading()
    }

    private fun removeLoading() {
        binding.searchLoading.visibility = View.GONE
    }

    private fun restoreLoading() {
        binding.searchLoading.visibility = View.VISIBLE
    }
}
