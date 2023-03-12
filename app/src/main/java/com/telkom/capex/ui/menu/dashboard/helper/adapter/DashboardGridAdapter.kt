package com.telkom.capex.ui.menu.dashboard.helper.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardGridModel

class DashboardGridAdapter(val gridMenu: ArrayList<DashboardGridModel>) : RecyclerView.Adapter<DashboardGridAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_dash_card, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val data = gridMenu[position]

        view.apply {
            findViewById<TextView>(R.id.card_sub).text = data.getSub()
            findViewById<TextView>(R.id.card_title).text = data.getTitle()
            if (data.getColor().isNotEmpty())
                findViewById<CardView>(R.id.card_root).setCardBackgroundColor(
                    Color.parseColor(
                        data.getColor()
                    )
                )

            setOnClickListener {
            }
        }
    }

    override fun getItemCount(): Int = gridMenu.size


}