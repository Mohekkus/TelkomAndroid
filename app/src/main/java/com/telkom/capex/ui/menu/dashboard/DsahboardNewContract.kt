package com.telkom.capex.ui.menu.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.FragmentNewContract1Binding

class DsahboardNewContract: Fragment() {

    lateinit var binding: FragmentNewContract1Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonSaveContract.setOnClickListener {
                when {
//                    etContractName.text == null -> etContractName.error = "Must be filled"
//                    etOrganizationName.text == null -> etContractName.error = "Must be filled"
//                    etContractValue.text == null -> etContractName.error = "Must be filled"
//                    etDateEDC.text == null -> etContractName.error = "Must be filled"
//                    etDateTOC.text == null -> etContractName.error = "Must be filled"
//                    etDescription.text == null -> etContractName.error = "Must be filled"
//                    etPartnerName.text == null -> etContractName.error = "Must be filled"
                    else -> initiateInsert()
                }
            }
        }
    }

    private fun initiateInsert() {
        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
    }
}