package com.telkom.capex.ui.menu.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetContainerBinding
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.etc.ToMiddleScroller
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.budget.fragments.BudgetList
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import com.telkom.capex.ui.menu.dashboard.helper.fragments.DashboardDialog
import com.telkom.capex.ui.menu.search.model.SharedSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Year
import java.util.Calendar

@AndroidEntryPoint
class BudgetFragment : Fragment()  {

    lateinit var binding: FragmentBudgetContainerBinding

    private val viewmodel by hiltNavGraphViewModels<BudgetSharedViewModel>(R.id.mobile_navigation)
    private val shared: SharedSearchViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetContainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.apply {
            monthList.observe(requireActivity()) {
                if (setData.value?.groupBy { it?.monthReferences }?.containsKey(it) == false)
                    getDataList()
            }
            year.observe(requireActivity()) {
                binding.rvMonthYear.adapter?.notifyDataSetChanged()
            }
            data.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it == null) return@observe
                        it.data?.let { data ->
                            val result = data.result?.get(0)
                            if (result != null)
                                addListData(
                                    year.value ?: Calendar.getInstance().get(Calendar.YEAR),
                                    monthList.value ?: MonthModifier.currentMonthInt(),
                                    result
                                )
                        }
                        setRefreshing(true)
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            if (binding.budgetRefresh.isRefreshing)
                setRefreshing(true)
        }

        binding.apply {
            ivBudgetSearch.setOnClickListener {
                shared.setFlagTo(SharedSearchViewModel.DICTIONARY.BUDGET)
                findNavController().navigate(R.id.action_budgetlist_to_fragmentSearchContract)
            }
            rvMonthYearOverlay.setOnClickListener {
                DashboardDialog(
                    viewmodel.year.value ?: Year.now().value,
                    viewmodel.monthList.value ?: MonthModifier.currentMonthInt()
                ).apply {
                    setListener { _, year, month, _ ->
                        viewmodel.apply {
                            if (month != monthList.value)
                                setMonthList(month)
                            if (year != viewmodel.year.value)
                                setYear(year)
                        }
                    }
                }.show(childFragmentManager, "YeMonth Picker")
            }
            rvMonthYear.apply {
                //Scroll To Middle
                layoutManager = ToMiddleScroller(requireContext(), LinearLayoutManager.HORIZONTAL, false)


                //Adapter with first and last item condition (to achieve everything is in middle)
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(parent.context)
                                .inflate(R.layout.component_budget_mmyy, parent, false)
                        )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val itemView = holder.itemView

                        itemView.findViewById<TextView>(R.id.tv_item).apply {
                            val bulan = MonthModifier.getMonth(position + 1)
                            text = "$bulan ${viewmodel.year.value}"

                            viewmodel.apply {
                                monthList.observe(viewLifecycleOwner) {
                                    if (it == position)
                                        setTextColor(
                                            ContextCompat.getColor(
                                                requireContext(),
                                                R.color.primary
                                            )
                                        )
                                    else
                                        setTextColor(
                                            ContextCompat.getColor(requireContext(), R.color.green_darkest)
                                        )
                                }
                            }

                            val absoluteMiddlePx = (this.measuredWidth * .5).toInt()
                            val measured = (this.measuredWidth * .5).toInt()
                            val marginOffset = (16 * resources.displayMetrics.density).toInt()
                            val absoluteMeasured = measured + marginOffset

                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                if (position == 0 && position != 9)
                                    setMargins(absoluteMiddlePx - absoluteMeasured,0,0,0)
                                if (position == 11)
                                    setMargins(0,0, absoluteMiddlePx - absoluteMeasured,0)
                            }
                        }
                    }
                    override fun getItemCount(): Int = 12
                }
            }
            budgetPagerList.apply {
                adapter = object : FragmentStateAdapter(this@BudgetFragment) {
                    override fun getItemCount(): Int = 12

                    override fun createFragment(position: Int): Fragment {
                        return BudgetList(position)
                    }
                }
                viewmodel.apply {
                    enableListener.observe(requireActivity()) {
                        lifecycleScope.launch {
                            delay(2000)
                            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    if (position != monthList.value) setMonthList(position)
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.apply {
            monthList.observe(requireActivity()) {
                binding.apply {
                    rvMonthYear.smoothScrollToPosition(it)
                    budgetPagerList.setCurrentItem(it, true)
                }
                if (enableListener.value == false)
                    setEnableListener(true)
            }
        }
    }
}