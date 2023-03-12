package com.telkom.capex.ui.menu.tracker.fragments.doc

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.ComponentDocPromptBinding
import com.telkom.capex.databinding.FragmentDocDetailBinding

class DOCDetailFragment: Fragment() {

    private lateinit var binding: FragmentDocDetailBinding

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

        binding.apply {

            when (arguments?.isEmpty == null) {
                false -> {
                    btnEdit.visibility = View.GONE
                    containerProgress.visibility = View.GONE
                    etextDescription.isEnabled = false
                }
                else -> return
            }

            btnEdit.setOnClickListener {
                toggleUI()
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