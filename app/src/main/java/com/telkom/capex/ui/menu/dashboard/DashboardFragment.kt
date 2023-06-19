package com.telkom.capex.ui.menu.dashboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentDashboardBinding
import com.telkom.capex.etc.ChartDemo
import com.telkom.capex.etc.Utility
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.dashboard.helper.MyJavascriptInterface
import com.telkom.capex.ui.menu.dashboard.helper.fragments.DashboardDialog
import com.telkom.capex.ui.menu.dashboard.helper.fragments.MonthlyBastFragment
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardYearResultItem
import com.telkom.capex.ui.menu.search.model.SharedSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

//    private val viewModel by viewModels<DashboardViewModel>()
    private val viewModel: DashboardViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private val shared: SharedSearchViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    @Inject lateinit var utility: Utility
    private var isExpanding: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            isRefreshing.observe(requireActivity()) {
                if (it) getDashboard()
            }
            division.observe(requireActivity()) { handler ->
                when (handler.status) {
                    Status.SUCCESS -> {
                        handler.data.let { res ->
                            Log.e("Division", res?.result?.get(0)?.intidorg.toString())
                            if (res?.result?.get(0)?.intidorg != null) getPie(res.result[0].intidorg ?: 0)
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
            dashboard.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { dashboardResponse ->
                            val result = dashboardResponse?.result
                            result?.get(0)?.apply {
                                assembleData(bintsumbast, intcountkont, bintsumnikon, bintsisapekerjaan, bintbastyear, intcountkontrakaktif, intcountcontrakselesaiNBSP)
                            }
                        }
                        if (isRefreshing.value == true) setRefreshing(false)
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
            binding.apply {
                page.observe(viewLifecycleOwner) {
                    dashboardPagerMonth.currentItem = it
                }
                year.observe(viewLifecycleOwner) {
                    dashboardTextYear.text = it.toString()
                }
            }

        }

        initiateActivity()
    }

    private fun initiateActivity() {
        binding.apply {
            utility.timelyGreet.setGreeting(dashTime)
            dashboardRefresh.setOnRefreshListener {
                viewModel.setRefreshing(true)
            }
            mehLottie.apply {
                setOnClickListener {
                    playAnimation()
                    soonTM()
                }
            }
            dashFcviewSpinner.apply {
                parentFragmentManager.findFragmentById(this.id)
            }

            dashboardChangeMonthYear.setOnClickListener {
                DashboardDialog(
                    viewModel.year.value?.toInt() ?: Calendar.getInstance().get(Calendar.YEAR),
                    dashboardPagerMonth.currentItem
                ).apply {
                    setListener { _, y, m, _ ->
                        viewModel.apply {
                            setPage(m)
                            setYear(y)
                        }
                    }
                }.show(childFragmentManager, "YeMonth Picker")
            }
            dashboardContractCollapsedDetail.setOnClickListener {
                isExpanding = when (isExpanding) {
                    true -> {
                        dashboardContractCollapsedIndicator.text = getString(R.string.expanding_string)
                        dashboardContractCollapsedIconAnimated.apply {
                            setMaxFrame(12)
                            resumeAnimation()
                            utility.animUtils.apply {
                                expand(dashboardContractCollapsedMiniView)
                                collapse(dashboardContractCollapsedDetailedView)
                            }
                        }
                        false
                    }
                    false -> {
                        dashboardContractCollapsedIndicator.text = getString(R.string.collapsing_string)
                        dashboardContractCollapsedIconAnimated.apply {
                            setMaxFrame(6)
                            playAnimation()
                            utility.animUtils.apply {
                                collapse(dashboardContractCollapsedMiniView)
                                expand(dashboardContractCollapsedDetailedView)
                            }
                        }
                        true
                    }
                }
            }
            dashboardSmilePiechart.apply {
                setBackgroundColor(Color.TRANSPARENT)
                webChromeClient = WebChromeClient()
                settings.apply {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
                addJavascriptInterface(MyJavascriptInterface(requireContext()), "Android")
                viewModel.apply {
                    pie.observe(requireActivity()) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                dashboardSmilePiechart.visibility = View.VISIBLE
                                dashboardSmileLoading.visibility = View.GONE
                                it.data.let { dashboardResponse ->
                                    val result = dashboardResponse?.result

                                    loadDataWithBaseURL(
                                        null,
                                        ChartDemo.getPie(result?.get(0)?.status),
                                        "text/html",
                                        "UTF-8",
                                        null
                                    )
                                }
                            }
                            Status.LOADING -> {
                                dashboardSmilePiechart.visibility = View.GONE
                                dashboardSmileLoading.visibility = View.VISIBLE
                            }
                            Status.ERROR -> {
                                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
            dashboardCardNew.setOnClickListener {
                shared.setFlagTo(SharedSearchViewModel.DICTIONARY.CONTRACT)
                findNavController().navigate(R.id.action_navigation_dashboard_to_fragmentSearchContract)
            }
            dashboardSearchContract.setOnClickListener {
                shared.setFlagTo(SharedSearchViewModel.DICTIONARY.CONTRACT)
                findNavController().navigate(R.id.action_navigation_dashboard_to_fragmentSearchContract)
            }
        }
    }
    private fun soonTM() {
        Snackbar.make(binding.root, "Soon but better version", Snackbar.LENGTH_LONG).show()
    }

    private fun assembleData(
        bastTotal: Long,
        bastValue: Long,
        bastContracts: Long,
        bastLeft: Long,
        bastdone: Long,
        active: Long,
        inactive: Long
    ) {
        binding.apply {
            dashboardOverviewTotalContracts.text = bastValue.toString()
            dashboardOverviewTotalContractActive.text = active.toString()
            dashboardOverviewTotalContractDone.text = inactive.toString()
            utility.money.apply {
                dashboardOverviewTotalValue.text = format(bastTotal)
                dashboardDetailedContractRemain.text = format(bastLeft)
                dashboardOverviewContractValue.text = format(bastContracts)
                dashboardDetailedContractBastDone.text = format(bastdone)
                dashboardDetailedTextBastDoneWYear.append(
                    viewModel.year.value.toString()
                )

                percentage(
                    bastContracts,
                    bastdone
                ).apply {
                    dashboardProgressBastDone.progress = this
                    dashboardOverviewPercentageDone.text = "$this%"
                    dashboardDetailedPercentageDone.text = "$this%"
                }

                percentage(
                    bastContracts, bastLeft
                ).apply {
                    dashboardProgressContractValue.progress = this
                    dashboardOverviewPercentageRemain.text = "$this%"
                    dashboardDetailedPercentageRemain.text = "$this%"
                }
            }

            dashboardRefresh.isRefreshing = false
        }
    }

    private fun checkBarData(resultChart: DashboardYearResultItem) {
        binding.apply {
            dashboardTextYear.text = viewModel.year.value.toString()
            
            if (resultChart.arrays.isNullOrEmpty() || resultChart.arrays.sum() < 1) {
                dataNullBarChart()
                return
            }

            dashboardPagerMonth.apply {
                if (visibility == View.GONE) {
                    visibility = View.VISIBLE
                    dashboardPagerTextNull.text = ""
                }

                adapter = object : FragmentStateAdapter(this@DashboardFragment) {
                    override fun getItemCount(): Int = resultChart.arrays.size


                    override fun createFragment(position: Int): Fragment =
                        MonthlyBastFragment(resultChart.arrays[position], position + 1)
                }

                currentItem = Calendar.getInstance().get(Calendar.MONTH)
            }

            dashboardBastBarchart.apply {
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
                        dashboardPagerMonth.setCurrentItem(columnIndex, true)
                    }
                }, "Android")
            }
        }

    }

    private fun dataNullBarChart() {
        binding.apply {
            dashboardPagerMonth.visibility = View.GONE
            dashboardBastBarchart.visibility = View.GONE

            dashboardPagerTextNull.text = getString(R.string.array_null_data)
        }
    }
}