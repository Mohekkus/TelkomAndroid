package com.telkom.capex.ui.tracker.fragments.doc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.FragmentDocDetailBinding

class DOCDetailFragment: Fragment() {

    private lateinit var binding: FragmentDocDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDocDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStop() {
        super.onStop()
    }

}