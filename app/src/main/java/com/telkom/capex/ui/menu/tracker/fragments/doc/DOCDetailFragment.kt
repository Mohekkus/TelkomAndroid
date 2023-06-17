package com.telkom.capex.ui.menu.tracker.fragments.doc

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentDocPromptBinding
import com.telkom.capex.databinding.FragmentDocDetailBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.tracker.TrackerViewModel
import com.telkom.capex.ui.menu.tracker.fragments.doc.model.ResultDOC
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DOCDetailFragment: Fragment() {

    private lateinit var binding: FragmentDocDetailBinding
//    private val viewModel by viewModels<TrackerViewModel>()
    private val viewModel by hiltNavGraphViewModels<TrackerViewModel>(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDocDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            getData(viewModel.selected.value.toString())
            data.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { data ->
                            val result = data?.result
                            result?.get(0)?.apply {
                                assembleData(this)
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
//                btnEdit.setOnClickListener {
//                    toggleUI()
//                }

//                progress.observe(requireActivity()) {
//                    docSeekbar2.progress = it
//                    when (it) {
//                        0 -> {
//                            radioGroup.check(
//                                radOsm.id
//                            )
//                            tvApprovalDocDetail.text = "OSM"
//                        }
//                        1 -> {
//                            radioGroup.check(
//                                radDeputy.id
//                            )
//                            tvApprovalDocDetail.text = "Deputy"
//                        }
//                        2 -> {
//                            radioGroup.check(
//                                radEgm.id
//                            )
//                            tvApprovalDocDetail.text = "EGM"
//                        }
//                        else -> {
//                            radioGroup.check(
//                                radDone.id
//                            )
//                            tvApprovalDocDetail.text = "Done"
//                        }
//                    }
//                }
            }
        }
    }

    private fun assembleData(data: ResultDOC) {
        viewModel.setProgress(data.contractProgress)
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        binding.apply {
//            overviewTitle2.text = data.contractName
            data.apply {
//                tvMitraDocDetail.text = contractPartner
//                tvDescriptionDocDetail.text = contractDescription
//                tvUnitDocDetail.text = contractUnit
//                tvEdcDocDetail.text = contractEDC?.let {
//                    dateFormat.parse(it)
//                        ?.let { date -> formatter.format(date).toString() }
//                }
//                tvTocDocDetail.text = contractTOC?.let {
//                    dateFormat.parse(it)
//                        ?.let { date -> formatter.format(date).toString() }
//                }
//                docSeekbar2.isEnabled = false
//                etextDescription.setText(contractDocDescription.toString())
            }
        }
    }

    private var toggleEdit = false
    private var dataMutable = mutableSetOf<String>()
    private fun toggleUI() {
        binding.apply {
            when (toggleEdit) {
                true -> {
//                    onEditedData()
//                    etextDescription.apply {
//                        isEnabled = false
//                        clearFocus()
//                        KeyboardUtils.hide(requireActivity(), this)
//                    }
//                    docSeekbar2.isEnabled = false
//                    containerProgress.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
//                    toggleEdit = !toggleEdit
                }
                else -> {
//                    onEditingData()
//                    etextDescription.apply {
//                        isEnabled = true
//                        requestFocus()
//                        selectAll()
//                        KeyboardUtils.show(requireActivity(), this)
//                    }
//                    docSeekbar2.isEnabled = true
//                    containerProgress.layoutParams =
//                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
//                            setMargins(32,0,32,0)
//                        }
                    toggleEdit = !toggleEdit
                }
            }
        }
    }

    private fun radioButtonReference(checkedRadioButtonId: Int): Int {
        return when (checkedRadioButtonId) {
//            binding.radOsm.id -> 1
//            binding.radDeputy.id -> 2
//            binding.radEgm.id -> 3
//            binding.radDone.id -> 4
            else -> -1
        }
    }

    private fun onEditingData() {
        binding.apply {
            dataMutable.apply {
//                if (dataMutable.isNotEmpty()) dataMutable.clear()
//                add(radioButtonReference(radioGroup.checkedRadioButtonId).toString())
//                add(etextDescription.text.toString())
            }
//            docSeekbar2.setOnSeekBarChangeListener( object : OnSeekBarChangeListener {
//                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                    viewModel.setProgress(p1)
//                }
//                override fun onStartTrackingTouch(p0: SeekBar?) {}
//                override fun onStopTrackingTouch(p0: SeekBar?) {}
//            })
//            radioGroup.setOnCheckedChangeListener { _, p1 ->  viewModel.setProgress(
//                radioButtonReference(p1) - 1
//            )}
        }
    }

    private fun onEditedData() {
        binding.apply {
            dataMutable.apply {
//                if ( contains(
//                        radioButtonReference(
//                            radioGroup.checkedRadioButtonId
//                        ).toString()
//                    ) && contains(etextDescription.text.toString())
//                ) return

                val alertView = ComponentDocPromptBinding.inflate(layoutInflater)
                AlertDialog.Builder(requireContext()).apply {
                    setView(
                        alertView.root
                    )
                }.show().apply {
//                    alertView.buttonPositive.setOnClickListener {
//                        //perform network call
//                        this.dismiss()
//                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (arguments?.isEmpty == false)
            arguments = null
    }
}