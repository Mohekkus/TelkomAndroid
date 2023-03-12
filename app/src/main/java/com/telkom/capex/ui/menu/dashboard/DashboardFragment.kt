package com.telkom.capex.ui.menu.dashboard

import android.content.res.AssetManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentDashboardBinding
import com.telkom.capex.etc.Utility
import com.telkom.capex.ui.menu.dashboard.fragments.DashboardDialog
import com.telkom.capex.ui.menu.dashboard.fragments.MonthlyBastFragment
import com.telkom.capex.etc.ChartDemo
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.dashboard.helper.model.ResultChart
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<DashboardViewModel>()
    @Inject lateinit var utility: Utility
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
        viewModel.apply {
            dashboard.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { dashboardResponse ->
                            val result = dashboardResponse?.result
                            result?.get(0)?.apply {
                                assembleData(
                                    bastTotal,
                                    bastDone,
                                    nikon,
                                    remainJob
                                )
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
            bastYear.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { dashboardResponse ->
                            val result = dashboardResponse?.result
                            result?.get(0)?.apply {
                                checkBarData(this)
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

        initiateActivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initiateActivity() {
        binding.apply {
            utility.timelyGreet.setGreeting(dashTime)
            dashRefresh.setOnRefreshListener {

            }
            mehLottie.setOnClickListener {
                mehLottie.playAnimation()
                soonTM()
            }
            dashFcviewSpinner.apply {
                parentFragmentManager.findFragmentById(this.id).apply {
//                    (this as UnitSpinnerFragment).setCallback(::setChartText)
                }
            }
            dashKontrak.setOnClickListener {
                isExpanding = when (isExpanding) {
                    true -> {
                        textView6.text = getString(R.string.expanding_string)
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
                        textView6.text = getString(R.string.collapsing_string)
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
//            dashPagerMonth.apply {
//                val om = ObjectMapper()
//                val raw = om.readValue(requireActivity().assets.readAssetFile(), MonthlyBast::class.java)
//
//                viewModel.apply {
//                    setSize(raw.data?.size ?: 0)
//                    page.observe(viewLifecycleOwner) {
//                        currentItem = it
//                    }
//                    year.observe(viewLifecycleOwner) {
//                        dashYearHolder.text = it.toString()
//                    }
//                }
//
//                adapter = object : FragmentStateAdapter(this@DashboardFragment) {
//                    override fun getItemCount(): Int = raw.data?.size ?: 0
//
//                    override fun createFragment(position: Int): Fragment =
//                        MonthlyBastFragment().apply {
//                            setData(raw.data?.get(position) ?: DataItem())
//                        }
//                }
//
//                currentItem = Calendar.getInstance().get(Calendar.MONTH)
//
//                dashSelectMonth.setOnClickListener {
//                    DashboardDialog().apply {
//                        setListener { view, y, m ,d  ->
//                            viewModel.apply {
//                                setPage(m)
//                                setYear(y)
//                            }
//                        }
//                    }.show(childFragmentManager, "YeMonth Picker")
//                }
//            }
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
//            dashColChart.apply {
//                setBackgroundColor(Color.TRANSPARENT)
//                settings.apply {
//                    domStorageEnabled = true
//                    javaScriptEnabled = true
//                }
//                loadDataWithBaseURL(
//                    null,
//                    ChartDemo.getColumn(),
//                    "text/html",
//                    "UTF-8",
//                    null
//                )
//            }
            dashCardNew.apply {
                setOnClickListener {
                    findNavController().navigate(R.id.action_navigation_dashboard_to_newContractFragment)
//                    soonTM()
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

    private fun assembleData(bastTotal: Long, bastDone: Long, nikon: Long, remainJob: Long) {
        binding.apply {
            utility.money.apply {
                dashTotal.text = format(bastTotal)
                dashBastYr.text = format(bastDone)
                dashNikon.text = format(nikon)
                dashRemain.text = format(remainJob)

//                percentage(nikon, bastDone).apply {
//                    dashPercentageContractBast.text = this
//                    dashPercentageContractBast.text = this
//                }

                percentage(nikon, remainJob).apply {
                    dashPercentageContractRemain.text = this
                    dashPercentageContractRemainExpand.text = this
                }
            }

            dashRefresh.isRefreshing = false
        }
    }

    private fun checkBarData(resultChart: ResultChart) {
        binding.apply {
            dashYearHolder.text = viewModel.year.value.toString()
            
            if (resultChart.arrays.isNullOrEmpty()) {
                dataNullBarChart()
                return
            }

            dashPagerMonth.apply {
                if (resultChart.arrays.sum() < 1) {
                    return
                }

                if (visibility == View.GONE)
                    visibility = View.VISIBLE

                viewModel.apply {
                    setSize(resultChart.arrays.size)
                    page.observe(viewLifecycleOwner) {
                        currentItem = it
                    }
                    year.observe(viewLifecycleOwner) {
                        dashYearHolder.text = it.toString()
                    }
                }

                adapter = object : FragmentStateAdapter(this@DashboardFragment) {
                    override fun getItemCount(): Int = resultChart.arrays?.size ?: 0

                    override fun createFragment(position: Int): Fragment =
                        MonthlyBastFragment().apply {
                            setData(resultChart.arrays[position])
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
            dashColChart.apply {
                if (resultChart.arrays.sum() < 1) {
                    return
                }

                if (visibility == View.GONE)
                    visibility = View.VISIBLE

                setBackgroundColor(Color.TRANSPARENT)
                webChromeClient = WebChromeClient()
                settings.apply {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
                loadDataWithBaseURL(
                    null,
                    ChartDemo.getColumn(resultChart.arrays),
                    "text/html",
                    "UTF-8",
                    null
                )
                addJavascriptInterface( object {
                    @JavascriptInterface
                    fun onClick(columnIndex : Int) {
                        dashPagerMonth.setCurrentItem(columnIndex, true)
                    }
                }, "Android")
            }
        }

    }

    private fun dataNullBarChart() {
        binding.apply {
//            dashPagerMonth.visibility = View.GONE
            dashColChart.visibility = View.GONE
        }
    }

//    override fun doWithData(pojo: DashboardModel) {
//        binding.apply {
//            dashTotal.text = "IDR ${pojo.bastTotal}"
//            dashRvGrid.apply {
//                isNestedScrollingEnabled = false
//                isScrollContainer = false
//                layoutManager = GridLayoutManager(requireContext(), 2)
//                adapter = DashboardGridAdapter(pojo.gridMenu)
//                setHasFixedSize(true)
//            }
//            dashRefresh.isRefreshing = false
//        }
//    }
//
//    override fun setCurrentPageTo(position: Int) {
//        binding.dashPagerMonth.currentItem = position
//    }
}