package com.telkom.capex.menu.dashboard.helper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.menu.dashboard.fragments.DashboardDialog

class DashboardDialogAdapter(
    private val dialog: DashboardDialog,
    private val size: Int,
    private val setPage: (Int) -> Unit,
) : RecyclerView.Adapter<DashboardDialogAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_dash_card, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val layout = holder.itemView
        val data = MonthModifier.getMonthPrefix(position + 1)

        layout.apply {
            findViewById<ImageView>(R.id.imageView11).visibility = View.GONE
            findViewById<TextView>(R.id.card_sub).apply {
                text = data
                textSize = 16f
            }
            findViewById<TextView>(R.id.card_title).visibility = View.GONE
            setOnClickListener {
                setPage(position)
                dialog.dismiss()
            }
        }
    }

    override fun getItemCount(): Int = size
}