package com.telkom.capex.ui.menu.tracker.fragments.bop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.FragmentBopDetailBinding

class BOPDetailFragment: Fragment() {

    lateinit var binding: FragmentBopDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBopDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}