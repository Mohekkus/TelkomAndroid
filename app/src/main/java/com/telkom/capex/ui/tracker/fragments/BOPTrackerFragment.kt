package com.telkom.capex.ui.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBopBinding

class BOPTrackerFragment: Fragment() {

    private lateinit var binding: FragmentBopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBopBinding.inflate(inflater, container, false)

        return binding.root
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bopRv.apply {
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
                        LayoutInflater.from(requireContext()).inflate(R.layout.component_bop, parent, false)
                    )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val itemview = holder.itemView

                        itemview.apply {

                            setOnClickListener {

                            }
                        }
                    }

                    override fun getItemCount(): Int = 3
                }
            }
        }
    }
}
