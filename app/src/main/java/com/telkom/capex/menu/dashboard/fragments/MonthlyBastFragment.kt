package com.telkom.capex.menu.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.telkom.capex.R
import com.telkom.capex.menu.dashboard.helper.model.DataItem

class MonthlyBastFragment: Fragment() {

    private lateinit var data: DataItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.component_dash_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
            setOnClickListener {
                soonTM()
            }
            data.apply {
                findViewById<TextView>(R.id.card_title).text = duit
                findViewById<TextView>(R.id.card_sub).text = month
            }
        }
    }

    fun setData(data: DataItem) {
        this.data = data
    }


    private fun soonTM() {
        Toast.makeText(this.context, "SOON", Toast.LENGTH_LONG).show()
    }
}