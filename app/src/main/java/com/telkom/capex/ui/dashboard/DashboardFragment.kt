package com.telkom.capex.ui.dashboard

import android.content.res.AssetManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentDashboardBinding
import com.telkom.capex.etc.Utility
import com.telkom.capex.ui.dashboard.fragments.DashboardDialog
import com.telkom.capex.ui.dashboard.fragments.MonthlyBastFragment
import com.telkom.capex.etc.ChartDemo
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.ui.dashboard.helper.DashboardFirestore
import com.telkom.capex.ui.dashboard.helper.adapter.DashboardGridAdapter
import com.telkom.capex.ui.dashboard.helper.interfaces.DashboardInterface
import com.telkom.capex.ui.dashboard.helper.model.DashboardModel
import com.telkom.capex.ui.dashboard.helper.model.DataItem
import com.telkom.capex.ui.dashboard.helper.model.MonthlyBast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(), DashboardInterface {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<DashboardViewModel>()

    @Inject lateinit var utility: Utility
    @Inject lateinit var dashboardFirestore: DashboardFirestore

    private var isExpanding: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initiateActivity()
    }

    private fun getData() {
        dashboardFirestore.getDoc(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initiateActivity() {
        binding.apply {
            utility.timelyGreet.setGreeting(dashTime)
            dashRefresh.setOnRefreshListener {
                getData()
            }
            mehLottie.setOnClickListener {
                mehLottie.playAnimation()
                soonTM()
//                Toast.makeText(this@DashboardActivity, "Kontol, eh, kontol", Toast.LENGTH_LONG).show()
            }
            dashFcviewSpinner.apply {
                parentFragmentManager.findFragmentById(this.id).apply {
//                    (this as UnitSpinnerFragment).setCallback(::setChartText)
                }
            }
            dashKontrak.setOnClickListener {
                isExpanding = when (isExpanding) {
                    true -> {
                        textView6.text = "expand"
                        dashExpand.apply {
                            setMaxFrame(12)
                            resumeAnimation()
                            utility.animUtils.apply {
                                expand(dashPercentage)
                                collapse(dashDetail)
                            }
                        }
                        false
                    }
                    false -> {
                        textView6.text = "collapse"
                        dashExpand.apply {
                            setMaxFrame(6)
                            playAnimation()
                            utility.animUtils.apply {
                                collapse(dashPercentage)
                                expand(dashDetail)
                            }
                        }
                        true
                    }
                }
            }
            dashPagerMonth.apply {
                val om = ObjectMapper()
                val raw = om.readValue(requireActivity().assets.readAssetFile(), MonthlyBast::class.java)

                viewModel.apply {
                    setSize(raw.data?.size ?: 0)
                    page.observe(viewLifecycleOwner) {
                        currentItem = it
                    }
                    year.observe(viewLifecycleOwner) {
                        dashYearHolder.text = it.toString()
                    }
                }

                adapter = object : FragmentStateAdapter(this@DashboardFragment) {
                    override fun getItemCount(): Int = raw.data?.size ?: 0

                    override fun createFragment(position: Int): Fragment =
                        MonthlyBastFragment().apply {
                            setData(raw.data?.get(position) ?: DataItem())
                        }
                }

                currentItem = Calendar.getInstance().get(Calendar.MONTH)

                dashSelectMonth.setOnClickListener {
                    DashboardDialog().apply {
                        setListener { view, y, m ,d  ->
                            viewModel.apply {
                                setPage(m)
                                setYear(y)
                            }
                        }
                    }.show(childFragmentManager, "YeMonth Picker")
                }
            }
            dashPieChart.apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.apply {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
                loadDataWithBaseURL(
                    null,
                    ChartDemo.getPie(),
                    "text/html",
                    "UTF-8",
                    null
                )
            }
            dashColChart.apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.apply {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
                loadDataWithBaseURL(
                    null,
                    ChartDemo.getColumn(),
                    "text/html",
                    "UTF-8",
                    null
                )
            }
            dashCardNew.apply {
                setOnClickListener {
                    soonTM()
                }
            }
            dashSearch.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_dashboard_to_dashboardSearchFragment)
            }
        }
    }

    private fun AssetManager.readAssetFile(): String = open("month_money.json").bufferedReader().use { it.readText() }

    private fun soonTM() {
        Snackbar.make(binding.root, "Soon but better version", Snackbar.LENGTH_LONG).show()
    }

    override fun doWithData(pojo: DashboardModel) {
        binding.apply {
            dashTotal.text = "IDR ${pojo.bastTotal}"
            dashRvGrid.apply {
                isNestedScrollingEnabled = false
                isScrollContainer = false
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = DashboardGridAdapter(pojo.gridMenu)
                setHasFixedSize(true)
            }
            dashRefresh.isRefreshing = false
        }
    }

    override fun setCurrentPageTo(position: Int) {
        binding.dashPagerMonth.currentItem = position
    }
}