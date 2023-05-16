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
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Year

@AndroidEntryPoint
class BudgetFragment : Fragment()  {

    private var _binding: FragmentBudgetContainerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewmodel by hiltNavGraphViewModels<BudgetViewModel>(R.id.mobile_navigation)
    private val shared by hiltNavGraphViewModels<BudgetSharedViewModel>(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetContainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared.apply {
            setYear(
                Year.now().value
            )

            data.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { it ->
                            val result = it?.result
                            result?.let { list -> addListData(list) }
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

        binding.apply {
            rvMonthYearOverlay.setOnClickListener {
                Snackbar.make(view, "Soon TM", Snackbar.LENGTH_LONG).show()
            }
            rvMonthYear.apply {
                //Scroll To Middle
                layoutManager = ToMiddleScroller(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                viewmodel.monthList.observe(viewLifecycleOwner) {
                    smoothScrollToPosition(it)
                }

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
                            text = "$bulan 2022"

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

                            val absoluteMiddlePx = (view.measuredWidth * .5).toInt()
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
                        return BudgetList()
                    }
                }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewmodel.setMonthList(position)
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            val currentMonth = MonthModifier.currentMonthInt() - 1

            rvMonthYear.smoothScrollToPosition(currentMonth)
            budgetPagerList.setCurrentItem(
                currentMonth,
                true
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}