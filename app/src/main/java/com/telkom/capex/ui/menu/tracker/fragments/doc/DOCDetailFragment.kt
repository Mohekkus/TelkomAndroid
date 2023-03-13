package com.telkom.capex.ui.menu.tracker.fragments.doc

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.databinding.ComponentDocPromptBinding
import com.telkom.capex.databinding.FragmentDocDetailBinding
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.tracker.fragments.doc.model.ResultDOC
import com.telkom.capex.ui.menu.tracker.fragments.doc.viewmodel.DetailDocViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DOCDetailFragment: Fragment() {

    private lateinit var binding: FragmentDocDetailBinding
    private val viewModel by activityViewModels<DetailDocViewModel>()
    private val args: DOCDetailFragmentArgs by navArgs()

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

        viewModel.data.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { data ->
                        val result = data?.result
                        result?.get(0)?.apply {
                            assembleData(
                                this
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

        binding.apply {

            btnEdit.setOnClickListener {
                toggleUI()
            }


            viewModel.getData(args.contractname.toString())
        }
    }

    private fun assembleData(data: ResultDOC) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        binding.apply {
            overviewTitle2.text = args.contractname
            data.apply {
                tvMitraDocDetail.text = contractPartner
                tvDescriptionDocDetail.text = contractDescription
                tvUnitDocDetail.text = contractUnit
                tvEdcDocDetail.text = contractEDC?.let {
                    dateFormat.parse(it)
                        ?.let { date -> formatter.format(date).toString() }
                }
                tvTocDocDetail.text = contractTOC?.let {
                    dateFormat.parse(it)
                        ?.let { date -> formatter.format(date).toString() }
                }
                docSeekbar2.apply {
                    isEnabled = false
                    progress = contractProgress
                }
                tvApprovalDocDetail.text =
                    when (contractProgress) {
                        0 -> "OSM"
                        1 -> "Deputy"
                        2 -> "EGM"
                        else -> "Done"
                    }
                etextDescription.setText(contractDocDescription.toString())
            }
        }
    }

    private var toggleEdit = false
    private var dataMutable = mutableSetOf<String>()
    private fun toggleUI() {
        binding.apply {
            when (toggleEdit) {
                true -> {
                    onEditedData()
                    etextDescription.isEnabled = false
                    containerProgress.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                    toggleEdit = !toggleEdit
                }
                else -> {
                    onEditingData()
                    etextDescription.isEnabled = true
                    containerProgress.layoutParams =
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                            setMargins(32,0,32,0)
                        }
                    toggleEdit = !toggleEdit
                }
            } }
    }

    private fun radioButtonReference(checkedRadioButtonId: Int): Int {
        return when (checkedRadioButtonId) {
            binding.radOsm.id -> 1
            binding.radDeputy.id -> 2
            binding.radEgm.id -> 3
            binding.radDone.id -> 4
            else -> -1
        }
    }

    private fun onEditingData() {
        binding.apply {
            dataMutable.apply {
                if (dataMutable.isNotEmpty()) dataMutable.clear()
                add(radioButtonReference(radioGroup.checkedRadioButtonId).toString())
                add(etextDescription.text.toString())
            }
        }
    }

    private fun onEditedData() {
        binding.apply {
            dataMutable.apply {
                if ( contains(
                        radioButtonReference(
                            radioGroup.checkedRadioButtonId
                        ).toString()
                    ) && contains(etextDescription.text.toString())
                ) return

                val alertView = ComponentDocPromptBinding.inflate(layoutInflater)
                AlertDialog.Builder(requireContext()).apply {
                    setView(
                        alertView.root
                    )
                }.show().apply {
                    alertView.buttonPositive.setOnClickListener {
                        //perform network call
                        this.dismiss()
                    }
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()

        if (arguments?.isEmpty == false)
            arguments = null
    }

}