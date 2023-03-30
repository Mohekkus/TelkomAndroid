package com.telkom.capex.ui.menu.dashboard.helper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.ComponentDashCardBinding
import com.telkom.capex.etc.Money
import com.telkom.capex.etc.MonthModifier

class MonthlyBastFragment(private val data: Long, private val position: Int) : Fragment() {

    private var _binding: ComponentDashCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ComponentDashCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            root.setOnClickListener {
                soonTM()
            }
            cardTitle.text = Money().format(data)
            cardSub.text = MonthModifier.getMonth(position)
        }
    }


    private fun soonTM() {
        Toast.makeText(this.context, "SOON", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}