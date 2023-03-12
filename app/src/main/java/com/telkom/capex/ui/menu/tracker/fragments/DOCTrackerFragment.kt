package com.telkom.capex.ui.menu.tracker.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentDocBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.etc.Utility
import com.telkom.capex.network.utility.Status
import com.telkom.capex.ui.menu.tracker.TrackerViewModel
import com.telkom.capex.ui.menu.tracker.fragments.doc.DOCDetailFragment
import com.telkom.capex.ui.menu.tracker.fragments.doc.model.ResultDOC
import com.telkom.capex.ui.menu.tracker.model.DOCFilter
import com.telkom.capex.ui.menu.tracker.model.DOCSelectedModel
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@AndroidEntryPoint
class DOCTrackerFragment: Fragment() {

    private lateinit var binding: FragmentDocBinding
    private val viewModel by activityViewModels<TrackerViewModel>()

    @Inject
    lateinit var utility: Utility

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDocBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.doc.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { response ->
                        val result = response?.result
                        result?.apply {
                            assembleData(this)
                            setupFilter(this)
                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun assembleData(resultDOCS: List<ResultDOC>) {
        binding.apply {
            setupSearch(resultDOCS)
            docRecycler.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(requireContext()).inflate(R.layout.component_doc, parent, false)
                        )

                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val data = resultDOCS[position]
                        holder.itemView.apply {
                            findViewById<TextView>(R.id.doc_title).text = data.contractName
                            findViewById<SeekBar>(R.id.doc_seekbar).apply {
                                isEnabled = false
                                progress = data.contractProgress
                            }
                            if (data.contractProgress == 3) {
                                findViewById<TextView>(R.id.doc_status).apply {
                                    text = "Done"
                                    setTextColor(
                                        Color.WHITE
                                    )
                                }
                                findViewById<LinearLayout>(R.id.doc_status_container).backgroundTintList =
                                    ColorStateList.valueOf(
                                        ContextCompat.getColor(requireContext(), R.color.green_darkest)
                                    )
                            }

                            val multipleModel = DOCSelectedModel(data)
                            setOnClickListener {
                                when (arguments?.isEmpty) {
                                    false -> arguments?.getBoolean("Guest").apply {
                                        if (this == true)
                                            parentFragmentManager.beginTransaction()
                                                .add(binding.root.id, DOCDetailFragment().apply {
                                                    arguments = when (arguments?.isEmpty) {
                                                        true -> null
                                                        else -> this@DOCTrackerFragment.arguments
                                                    }
                                                })
                                                .addToBackStack("Guest-Tracker-Detail")
                                                .commit()
                                    }
                                    else ->
                                        viewModel.selectingItemList.observe(viewLifecycleOwner) { value ->
                                            if (value == true)
                                                multipleSelect(multipleModel, holder)
                                            else
                                                findNavController().navigate(R.id.action_navigation_tracker_to_DOCDetailFragment) //Send request with date
                                        }
                                }
                            }

                            setOnLongClickListener {
                                if (arguments?.getBoolean("Guest") == true) return@setOnLongClickListener true

                                multipleSelect(multipleModel, holder)

                                return@setOnLongClickListener true
                            }
                        }
                    }
                    override fun getItemCount(): Int = resultDOCS.size
                }
            }
        }
    }

    private fun setupSearch(resultDOCS: List<ResultDOC>) {
        val searchHandler = Handler(Looper.getMainLooper())
        val searchRunnable = AtomicReference<Runnable>()

        binding.docSearch.apply {
            KeyboardVisibilityEvent.setEventListener(
                requireActivity()
            ) {
                if (!it) this.clearFocus()
            }

            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        KeyboardUtils.hide(requireActivity(), binding.root)
                        clearFocus()


                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        searchRunnable.get()?.let { searchHandler.removeCallbacks(it) }
                        val query = p0.orEmpty().trim().uppercase()

                        val data = resultDOCS.filter { it.contractName.contains(p0 ?: "", ignoreCase = true) }

                        if (data.isNotEmpty()) assembleData(data)
                        if (query.isNotEmpty()) {
                            viewModel.setSearchQuery(query)

                            val runnable = Runnable {
                                viewModel.searchFor(query)
                            }

                            searchRunnable.set(runnable)

                            searchHandler.postDelayed(runnable, 2000L)
                        } else
                            assembleData(viewModel.archive.value?.result ?: resultDOCS)

                        return true
                    }
                }
            )
        }
    }


    private fun setupFilter(resultDOCS: List<ResultDOC>) {
        val data = resultDOCS.map {
            it.contractStatus
        }

        binding.docFilter.apply {
            val tempModel = mutableListOf<DOCFilter>().apply {
                add(DOCFilter("All Status"))
                add(DOCFilter("Done (${data.count { !it }})"))
                add(DOCFilter("Progress (${data.count { it }})"))
            }
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = object : RecyclerView.Adapter<ViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                    ViewHolder(
                        LayoutInflater.from(requireContext())
                            .inflate(R.layout.component_filter, parent, false)
                    )


                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    val model = tempModel[position]

                    holder.itemView.apply {
                        findViewById<TextView>(R.id.filter_text).apply {
                            text  = model.getStatus()
                            viewModel.selectedFilter.observe(viewLifecycleOwner){
                                if (it == position){
                                    backgroundTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.primary
                                        )
                                    )
                                    setTextColor(Color.WHITE)
                                } else {
                                    backgroundTintList = null
                                    setTextColor(
                                        ContextCompat.getColor(requireContext(), R.color.green_darkest)
                                    )
                                }
                                filteringData(it, resultDOCS)
                            }

                            setOnClickListener {
                                viewModel.setSelectedFilter(position)
                            }
                        }
                    }
                }

                override fun getItemCount(): Int = 3
            }
        }
    }

    private fun filteringData(status: Int?, resultDOCS: List<ResultDOC>) {
        val data = when (status) {
            1 -> resultDOCS.filter { !it.contractStatus }
            2 -> resultDOCS.filter { it.contractStatus }
            else -> resultDOCS
        }

        assembleData(data)
    }
    fun multipleSelect(model: DOCSelectedModel, holder: ViewHolder) {
        model.apply {
            if (getProgress() != 2) {
                viewModel.setSelectedItemList(model)
                setItemSelected(!itemSelected())

                holder.itemView.apply {
                    Log.e("is Selected Item", itemSelected().toString())
                    if (itemSelected()) {
                        holder.itemView.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white_kinda
                            )
                        )
                    } else {
                        holder.itemView.setBackgroundColor(
                            Color.TRANSPARENT
                        )
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


class ViewHolder(view:View): RecyclerView.ViewHolder(view)
