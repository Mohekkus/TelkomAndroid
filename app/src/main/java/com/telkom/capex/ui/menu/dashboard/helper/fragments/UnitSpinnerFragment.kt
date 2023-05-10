package com.telkom.capex.ui.menu.dashboard.helper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentDashUnitSpinnerBinding

class UnitSpinnerFragment: Fragment() {

    private lateinit var binding: ComponentDashUnitSpinnerBinding
    private var viewID: Int? = null
    private lateinit var callback: (String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ComponentDashUnitSpinnerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinner.apply {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.Unit,
                viewID ?: R.layout.component_dash_spinner
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                this.adapter = adapter
//                callback(selectedItem.toString())
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
//                    parent?.getItemAtPosition(pos)
//                    callback(selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    fun setFragmentView(view: Int) {
        viewID = view
    }

    fun setCallback(callback: (String) -> Unit) {
        this.callback = callback
    }
}