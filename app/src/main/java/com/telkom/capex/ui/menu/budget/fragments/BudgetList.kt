package com.telkom.capex.ui.menu.budget.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentBudgetItemBinding
import com.telkom.capex.databinding.FragmentBudgetBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.etc.Utility
import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.time.Month
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class BudgetList(
    private val position: Int
    ) : Fragment() {

    private lateinit var binding: FragmentBudgetBinding
    @Inject lateinit var utility: Utility
    private val shared by hiltNavGraphViewModels<BudgetSharedViewModel>(R.id.mobile_navigation)
    private var listdata: BudgetListDataEntity? = null

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared.apply {
            isRefreshing.observe(requireActivity()) {
//                getListData(year.value ?: Calendar.getInstance().get(Calendar.YEAR))
            }
            setData.observe(requireActivity()) {
                setThisListData(setData.value)
            }
        }
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

            budgetRv.apply {
                isScrollContainer = false
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    lateinit var binding: ComponentBudgetItemBinding
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                        binding = ComponentBudgetItemBinding.inflate(layoutInflater, parent, false)
                        return ViewHolder(
                            binding.root
                        )
                    }

                    override fun getItemCount(): Int = listdata?.data?.size ?: 0

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
                            val data = listdata?.data?.get(position)
                            binding.apply {
                                data?.apply {
                                    docMitra.text = kontrakMitra
                                    docTitle.text = namaKontrak
                                    if (planningPM != null && actualPM != null)
                                        searchProgress.progress =
                                            ((planningPM.toDouble() / actualPM.toDouble()) * 100).roundToInt()

                                    utility.money.apply {
                                        holderPlan.text = format(planningPM ?: 0)
                                        holderTarget.text = format(actualPM ?: 0)
                                    }
                                }
                            }

                            setOnClickListener {
                                shared.getDetailContract( data?.idKontrak ?: 0)
                                findNavController().navigate(R.id.action_budgetlist_to_budgetDetail)
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setThisListData(mutableListOf: MutableList<BudgetListDataEntity?>?) {
        if (mutableListOf == null) return
        mutableListOf.forEach {
            if (it?.monthReferences == position ) {
                listdata = it
                binding.budgetRv.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun noData() {
        Toast.makeText(requireContext(), "No data at current timeline", Toast.LENGTH_LONG).show()
    }
}