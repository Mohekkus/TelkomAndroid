package com.telkom.capex.ui.menu.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

@AndroidEntryPoint
class BudgetList() : Fragment() {

    private lateinit var binding: FragmentBudgetBinding
    private val shared by hiltNavGraphViewModels<BudgetSharedViewModel>(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            budgetRv.apply {
                isScrollContainer = false
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(context)
                                .inflate(R.layout.component_budget_item, parent, false)
                        )

                    override fun getItemCount(): Int = 2

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val adapterview = holder.itemView

                        adapterview.apply {
                            if (position + 1  == itemCount) {
                                val rid = resources.getIdentifier("navigation_bar_height", "dimen", "android")
                                if (rid > 0) {
                                    adapterview.layoutParams = ViewGroup.MarginLayoutParams(adapterview.layoutParams).apply {
                                        bottomMargin = resources.getDimensionPixelSize(rid)
                                    }
                                }
                            }

                            setOnClickListener {
                                findNavController().navigate(R.id.action_budgetlist_to_budgetDetail)
                            }
                        }
                    }
                }
            }
            budgetListBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            budgetSearch.apply {
                KeyboardVisibilityEvent.setEventListener(
                    requireActivity()
                ) {
                    if (!it) this.clearFocus()
                }

                setOnQueryTextListener(
                    object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            KeyboardUtils.hide(requireActivity(), binding.root)
                            budgetSearch.clearFocus()

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