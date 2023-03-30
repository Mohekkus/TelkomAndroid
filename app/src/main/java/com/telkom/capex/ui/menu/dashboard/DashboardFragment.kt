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
import com.telkom.capex.etc.Utility
import com.telkom.capex.ui.menu.dashboard.helper.fragments.DashboardDialog
import com.telkom.capex.ui.menu.dashboard.helper.fragments.MonthlyBastFragment
import com.telkom.capex.etc.ChartDemo
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.dashboard.helper.model.ResultChart
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

//    private val viewModel by viewModels<DashboardViewModel>()
    private val viewModel: DashboardViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
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
            binding.apply {
                page.observe(viewLifecycleOwner) {
                    dashPagerMonth.currentItem = it
                }
                year.observe(viewLifecycleOwner) {
                    dashYearHolder.text = it.toString()
                }
            }

        }

        initiateActivity()
    }

    override fun onDestroyView() {
        Log.e("Destroyed", "Yes")
        super.onDestroyView()
        _binding = null
    }

    private fun initiateActivity() {
        binding.apply {
            utility.timelyGreet.setGreeting(dashTime)
            dashRefresh.setOnRefreshListener {

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

            dashSelectMonth.setOnClickListener {
                DashboardDialog(
                    viewModel.year.value?.toInt() ?: Calendar.getInstance().get(Calendar.YEAR),
                    dashPagerMonth.currentItem
                ).apply {
                    setListener { _, y, m, _ ->
                        viewModel.apply {
                            setPage(m)
                            setYear(y)
                        }
                    }
                }.show(childFragmentManager, "YeMonth Picker")
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
            dashPieChart.apply {
                setBackgroundColor(Color.TRANSPARENT)
                webChromeClient = WebChromeClient()
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
            dashCardNew.apply {
                setOnClickListener {
                    findNavController().navigate(R.id.action_navigation_dashboard_to_newContractFragment)
                }
            }
            dashSearch.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_dashboard_to_dashboardSearchFragment)
            }
        }
    }
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
            
            if (resultChart.arrays.isNullOrEmpty() || resultChart.arrays.sum() < 1) {
                dataNullBarChart()
                return
            }

            dashPagerMonth.apply {
                if (visibility == View.GONE) {
                    visibility = View.VISIBLE
                    tvNullData.text = ""
                }

                adapter = object : FragmentStateAdapter(this@DashboardFragment) {
                    override fun getItemCount(): Int = resultChart.arrays.size


                    override fun createFragment(position: Int): Fragment =
                        MonthlyBastFragment(resultChart.arrays[position], position + 1)
                }

                currentItem = Calendar.getInstance().get(Calendar.MONTH)
            }
            dashColChart.apply {
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
            dashPagerMonth.visibility = View.GONE
            dashColChart.visibility = View.GONE
            tvNullData.text = getString(R.string.array_null_data)
        }
    }
}