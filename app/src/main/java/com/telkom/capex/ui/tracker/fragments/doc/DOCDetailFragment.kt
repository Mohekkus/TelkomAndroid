package com.telkom.capex.ui.tracker.fragments.doc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

    private var toggleEdit = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            when (arguments?.isEmpty == null) {
                false -> {
                    btnEdit.visibility = View.GONE
                    containerProgress.visibility = View.GONE
                    etextDescription.isEnabled = false
                }
                else -> null
            }

            btnEdit.setOnClickListener {
                Log.e("breh", toggleEdit.toString())
                when (toggleEdit) {
                    true -> {
                        etextDescription.isEnabled = false
                        containerProgress.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                        toggleEdit = !toggleEdit
                        Log.e("toggleEdit", toggleEdit.toString())
                    }
                    else -> {
                        etextDescription.isEnabled = true
                        containerProgress.layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                                setMargins(16,0,16,0)
                            }
                        toggleEdit = !toggleEdit
                        Log.e("toggleEdit", toggleEdit.toString())
                    }
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()

        if (arguments?.isEmpty == false)
            arguments = null
    }

}