package com.telkom.capex.ui.menu.budget.fragments

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentBudgetCardBinding
import com.telkom.capex.databinding.ComponentBudgetDetailRvBinding
import com.telkom.capex.databinding.ComponentBudgetEditBinding
import com.telkom.capex.databinding.FragmentBudgetDetailBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.etc.Utility
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.ViewHolder
import com.telkom.capex.ui.menu.budget.helper.model.MonthlyDataItem
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BudgetDetailData : Fragment()  {

    private lateinit var binding: FragmentBudgetDetailBinding
    private val shared: BudgetSharedViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    @Inject lateinit var utility: Utility

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var rvBinding: ComponentBudgetDetailRvBinding
    private var listdata: List<MonthlyDataItem>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared.apply {
            monthlyData.observe(requireActivity()) { serviceHandler ->
                when (serviceHandler.status) {
                    Status.SUCCESS -> {
                        serviceHandler.data.let {
                            val result = it?.result
                            result?.let { res ->
                                listdata = res[0].mdata
                                binding.
                                    budgetContractsRecyclerView.adapter?.notifyDataSetChanged()
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
//            changesLog.observe(requireActivity()) {
//                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
//            }
        }
        binding.apply {

            budgetContractsRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                        rvBinding = ComponentBudgetDetailRvBinding.inflate(layoutInflater, parent, false)
                        return ViewHolder(rvBinding.root)
                    }

                    override fun getItemCount(): Int = listdata?.size ?: 0

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val data = listdata?.get(position)
                        val month = MonthModifier.getMonth( position + 1)
                        rvBinding.apply {
                            shared.editting.observe(requireActivity()) {
                                if (it) {
                                    imageEditable.visibility = View.VISIBLE
                                    textEditable.visibility = View.VISIBLE
                                    triggerEditable.setOnClickListener {
                                        shared.copyData(data, position)
                                        betterShittyRadioButton(
                                            data,
                                            "$month, ${shared.year.value}"
                                        )
                                    }
                                } else {
                                    imageEditable.visibility = View.GONE
                                    textEditable.visibility = View.GONE
                                    triggerEditable.setOnClickListener {  }
                                }
                            }
                            textBastMonth.text = "BAST $month"
                            textBastActual.text = utility
                                .money
                                .format(data?.actual ?: 0)
                            textBastPm.text = utility
                                .money
                                .format(data?.planningPM ?: 0)
                            textBastRkap.text = utility
                                .money
                                .format(data?.planningRKAP ?: 0)

                            shared.listCopiedData.observe(requireActivity()) {
                                if (it == null) {
                                    textBastActual.text = utility
                                        .money
                                        .format(data?.actual ?: 0)
                                    textBastPm.text = utility
                                        .money
                                        .format(data?.planningPM ?: 0)
                                    textBastRkap.text = utility
                                        .money
                                        .format(data?.planningRKAP ?: 0)
                                    textBastLog.apply {
                                        text = ""
                                        visibility = View.GONE
                                    }
                                } else
                                    for (pair in it) {
                                        val key = pair?.first
                                        val value = pair?.second

                                        if (key == position) {
                                            value?.apply {
                                                textBastActual.text = utility
                                                    .money
                                                    .format(actual)
                                                textBastPm.text = utility
                                                    .money
                                                    .format(planningPM)
                                                textBastRkap.text = utility
                                                    .money
                                                    .format(planningRKAP)
                                            }

                                            textBastLog.apply {
                                                visibility = View.VISIBLE
                                                var textTemp: String? = ""
                                                if (value?.actual != data?.actual)
                                                    textTemp += "Actual Changed from ${data?.actual} to ${value?.actual}\n"
                                                if (value?.planningPM != data?.planningPM)
                                                    textTemp += "PM Changed from ${data?.planningPM} to ${value?.planningPM}\n"
                                                if (value?.planningRKAP != data?.planningRKAP)
                                                    textTemp += "RKAP Changed from ${data?.planningRKAP} to ${value?.planningRKAP}\n"

                                                text =
                                                    if (textTemp == "") "Weird, I presence there is no data change"
                                                    else textTemp

                                                println(data?.planningPM)
                                                println(value?.planningPM)
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }

    private lateinit var botDialog: ComponentBudgetEditBinding
    lateinit var rvEditBinding: ComponentBudgetCardBinding
    private fun betterShittyRadioButton(data: MonthlyDataItem?, title: String) {

        botDialog = ComponentBudgetEditBinding.inflate(layoutInflater)
            .apply {
                textEditTitle.text = title
                edittextCurrentValue.setText((data?.planningPM ?: 0).toString())
                KeyboardUtils.show(requireActivity(), binding.root)
                rvEditOption.apply {
                    layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
                    adapter = object : RecyclerView.Adapter<ViewHolder>() {

                        private val params = listOf("PM", "RKAP", "BAST")

                        override fun onCreateViewHolder(
                            parent: ViewGroup,
                            viewType: Int
                        ): ViewHolder = ViewHolder(
                            ComponentBudgetCardBinding.inflate(
                                layoutInflater,
                                parent,
                                false
                            ).also {
                                rvEditBinding = it
                            }.root
                        )

                        override fun getItemCount(): Int = params.size

                        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                            edittextCurrentValue.apply {
                                doOnTextChanged { text, _, _, _ ->
                                    shared.updatedCopiedData(shared.selecting.value ?: 0, text.toString().toLong())
                                }
                                setOnEditorActionListener { _, id, _ ->
                                    if (id == EditorInfo.IME_ACTION_DONE) KeyboardUtils.hide(requireActivity(), this)
                                    true
                                }
                            }
                            holder.itemView.setOnClickListener {
                                shared.apply {
                                    selecting(position)
                                }
                                shared.copiedData.value?.second?.apply {
                                    edittextCurrentValue.setText(
                                        when (position) {
                                            0 -> planningPM.toString()
                                            1 -> planningRKAP.toString()
                                            else -> actual.toString()
                                        }
                                    )
                                }
                                KeyboardUtils.show(requireActivity(), it)
                            }
                            rvEditBinding.apply {
                                cardTitle.text = params[position]
                                shared.apply {
                                    selecting.observe(requireActivity()) {
                                        if (it == position){
                                            cardRoot.setBackgroundResource(
                                                R.drawable.drawable_solid_darkest
                                            )
                                            cardTitle.setTextColor(
                                                ContextCompat.getColor(requireContext(), R.color.white)
                                            )
                                            ivBudgetCal.isSelected = true
                                        }
                                        else {
                                            ivBudgetCal.isSelected = false
                                            cardTitle.setTextColor(
                                                ContextCompat.getColor(requireContext(), R.color.primary)
                                            )
                                            cardRoot.setBackgroundResource(
                                                R.drawable.drawable_border_primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                BottomSheetDialog(requireContext()).apply {
                    setContentView(root)
                    dismissWithAnimation = true
                    setOnShowListener {
                        val bottomSheet = findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
                        bottomSheet?.setBackgroundResource(R.drawable.drawable_rounded_top)
                    }
                    buttonEditRevise.setOnClickListener {
                        this.dismiss()
                    }
                    setOnDismissListener {
                        shared.apply {
                            if (copiedData.value != null) {
                                settleCopiedData()
                            }
                        }
                    }
                }.show()
            }
    }

    override fun onPause() {
        super.onPause()
        if (shared.editting.value == true) shared.setEditting(false)
    }
}