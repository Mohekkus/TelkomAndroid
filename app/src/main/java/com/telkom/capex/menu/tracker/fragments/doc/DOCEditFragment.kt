package com.telkom.capex.menu.tracker.fragments.doc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.FragmentDocEditBinding

class DOCEditFragment: Fragment() {

    lateinit var binding: FragmentDocEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDocEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}