package com.telkom.capex.ui.menu.dashboard.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentDashMonthBinding

class DashboardDialog(private val year: Int, val month: Int) : DialogFragment()  {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    private lateinit var binding: ComponentDashMonthBinding

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ComponentDashMonthBinding.inflate(requireActivity().layoutInflater)

        binding.pickerMonth.run {
            minValue = 0
            maxValue = 11
            value = month
            displayedValues = arrayOf("Jan","Feb","Mar","Apr","May","June","July",
                "Aug","Sep","Oct","Nov","Dec")
        }

        binding.pickerYear.run {
            minValue = year - 10
            maxValue = year + 10
            value = year
        }

        return AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Select your desire time frame")
            .setView(binding.root)
            .setPositiveButton("Ok") { _, _ ->
                listener?.onDateSet(
                    null,
                    binding.pickerYear.value,
                    binding.pickerMonth.value,
                    1
                )
            }
            .setNegativeButton("Cancel") { _, _ -> dialog?.cancel() }
            .create()
    }
}