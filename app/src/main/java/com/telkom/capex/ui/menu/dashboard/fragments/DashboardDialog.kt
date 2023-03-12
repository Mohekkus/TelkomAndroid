package com.telkom.capex.ui.menu.dashboard.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentDashMonthBinding
import com.telkom.capex.ui.menu.dashboard.DashboardViewModel
import java.util.*

class DashboardDialog: DialogFragment()  {

    private val viewModel by activityViewModels<DashboardViewModel>()

    companion object {
        private const val MAX_YEAR = 2099
    }

    private var listener: DatePickerDialog.OnDateSetListener? = null

    private lateinit var binding: ComponentDashMonthBinding

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ComponentDashMonthBinding.inflate(requireActivity().layoutInflater)
        val cal: Calendar = Calendar.getInstance()

        binding.pickerMonth.run {
            minValue = 0
            maxValue = 11
            value = cal.get(Calendar.MONTH)
            displayedValues = arrayOf("Jan","Feb","Mar","Apr","May","June","July",
                "Aug","Sep","Oct","Nov","Dec")
        }

        binding.pickerYear.run {
            val year = cal.get(Calendar.YEAR)
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