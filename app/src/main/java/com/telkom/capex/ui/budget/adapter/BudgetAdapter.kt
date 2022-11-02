package com.telkom.capex.ui.budget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.ui.budget.BudgetFragment

class BudgetAdapter(val fragment: BudgetFragment) : RecyclerView.Adapter<BudgetAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_budget_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView

        view.apply {
//
            if (position + 1  == itemCount) {
                val rid = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (rid > 0) {
                    view.layoutParams = ViewGroup.MarginLayoutParams(view.layoutParams).apply {
                        bottomMargin = resources.getDimensionPixelSize(rid);
                    }
                }
            }

            setOnClickListener {
//                fragment.childFragmentManager.beginTransaction()
//                    .add(fragment.view, DOCTrackerFragment)
            }
        }
    }

    override fun getItemCount(): Int = 4 + 1

}