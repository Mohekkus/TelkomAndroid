package com.telkom.capex.ui.menu.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentBudgetItemBinding
import com.telkom.capex.databinding.ComponentSearchItemBinding
import com.telkom.capex.databinding.LayoutSearchContractBinding
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import com.telkom.capex.ui.menu.search.model.SearchContractResultItem
import com.telkom.capex.ui.menu.search.model.SharedSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class FragmentSearchContract: Fragment() {

    lateinit var binding: LayoutSearchContractBinding
    private val viewModel: FragmentSearchViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private val shared: SharedSearchViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private val budgetvm: BudgetSharedViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    var searchResult: List<SearchContractResultItem>? = listOf()

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
                            searchResult = result
                            binding.rvContractList.adapter?.notifyDataSetChanged()
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
            tieNomorKontrak.apply {
                doOnTextChanged { text, start, before, count ->
                    viewModel.setQuery(text.toString())
                }
                doAfterTextChanged {
                    viewModel.apply {
                        if (viewModel.getQuery().value.isNullOrEmpty())
                            return@doAfterTextChanged

                        postQuery()
                    }
                }
            }

            button3.setOnClickListener {
                viewModel.apply {
                    if (viewModel.getQuery().value.isNullOrEmpty())
                        return@setOnClickListener

                    postQuery()
                }
            }
            binding.rvContractList.apply {
//            isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                        bindingRv = ComponentSearchItemBinding.inflate(layoutInflater, parent, false)
                        return ViewHolder(bindingRv.root)
                    }

                    override fun getItemCount(): Int = searchResult?.size ?: 0

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val itemView = holder.itemView
                        val data = searchResult?.get(position)
                        var simpleMath: Int? = null
                        data?.apply {
                            if (plan != 0L && target != 0L) {
                                simpleMath = ((plan.toDouble() / target.toDouble()) * 100).roundToInt()
                            }

                            bindingRv.apply {
                                searchKontrak.text = strnamakontrak
                                searchMitra.text = strnamaorg
                                searchPlan.text = plan.toString()
                                searchTarget.text = target.toString()
                                docStatus.text =
                                    if (status) "Active" else "Inactive"
                                docProgressPercentage.text = "${simpleMath.toString()}%"
                                searchProgress.progress = simpleMath ?: 0
                            }
                        }

                        itemView.setOnClickListener {
                            findNavController().navigate(R.id.action_fragmentSearchContract_to_budgetDetail)
                            budgetvm.getDetailContract(id = data?.intidkontrak ?: 0)
                            when (shared.getFlag().value) {
                                SharedSearchViewModel.DICTIONARY.CONTRACT -> {}
                                SharedSearchViewModel.DICTIONARY.BUDGET -> {}
                                else -> {}
                            }
                        }

                    }
                }
            }
        }
    }

    lateinit var bindingRv: ComponentSearchItemBinding
    private fun setRecyclerView() {

        removeLoading()
    }

    private fun removeLoading() {
        binding.searchLoading.visibility = View.GONE
    }

    private fun restoreLoading() {
        binding.searchLoading.visibility = View.VISIBLE
    }
}
