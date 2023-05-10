package com.telkom.capex.ui.menu.budget.helper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetingSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

@AndroidEntryPoint
class BudgetList() : Fragment() {

    private lateinit var binding: FragmentBudgetBinding
    private val viewModel by hiltNavGraphViewModels<BudgetingSharedViewModel>(R.id.mobile_navigation)

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

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
}