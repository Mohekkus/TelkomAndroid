package com.telkom.capex.ui.menu.budget

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
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
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBudgetContainerBinding
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.etc.ToMiddleScroller
import com.telkom.capex.ui.menu.budget.component.BudgetList
import com.telkom.capex.ui.menu.budget.fragments.component.ViewHolder
import com.telkom.capex.ui.menu.dashboard.helper.model.MonthlyBast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetFragment : Fragment()  {

    private var _binding: FragmentBudgetContainerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<BudgetViewModel>(R.id.mobile_navigation)

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
        binding.apply {
            rvMonthYearOverlay.setOnClickListener {
                Snackbar.make(view, "Soon TM", Snackbar.LENGTH_LONG).show()
            }
            rvMonthYear.apply {
                //Dummy Local Json File Mapper
                val om = ObjectMapper()
                val raw = om.readValue(requireActivity().assets.readAssetFile(), MonthlyBast::class.java)

                //Scroll To Middle
                layoutManager = ToMiddleScroller(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                viewModel.monthList.observe(viewLifecycleOwner) {
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
                            val data = raw.data?.get(position)
                            text = "${data?.month} 2022"

                            viewModel.apply {
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
                            val measure = this.measure(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
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

                //No user swipe to scroll by return true
                addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        return true
                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

                })

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
                        viewModel.setMonthList(position)
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            val currentMonth = MonthModifier.currentMonthInt() - 1

            budgetPagerList.setCurrentItem(
                currentMonth,
                true
            )
            rvMonthYear.smoothScrollToPosition(currentMonth)
        }
    }

    private fun AssetManager.readAssetFile(): String = open("month_money.json").bufferedReader().use { it.readText() }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}