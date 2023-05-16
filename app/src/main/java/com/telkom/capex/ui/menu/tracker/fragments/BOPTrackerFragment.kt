package com.telkom.capex.ui.menu.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentBopBinding
import com.telkom.capex.ui.menu.ViewHolder

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bopRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(requireContext())
                                .inflate(R.layout.component_bop, parent, false)
                        )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val itemview = holder.itemView

                        itemview.apply {
//                            findViewById<TextView>(R.id.textView49).text = "a$position"
                            setOnClickListener {
                                findNavController().navigate(R.id.action_navigation_tracker_to_BOPDetailFragment)
                            }
                        }
                    }

                    override fun getItemCount(): Int = 3
                }
            }
        }
    }
}
