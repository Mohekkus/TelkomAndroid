package com.telkom.capex.ui.tracker.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentDocBinding
import com.telkom.capex.etc.KeyboardUtils
import com.telkom.capex.etc.Utility
import com.telkom.capex.ui.tracker.TrackerViewModel
import com.telkom.capex.ui.tracker.model.DOCFilter
import com.telkom.capex.ui.tracker.model.DOCSelectedModel
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.util.*
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
        binding.apply {
            val mockModel = mutableListOf<DOCSelectedModel>().apply {
                add(0, DOCSelectedModel("ERR-666/69.420/01/2022", 1))
                add(1, DOCSelectedModel("ERR-666/69.420/02/2022", 2))
                add(2, DOCSelectedModel("ERR-666/69.420/03/2022", 0))
                add(3, DOCSelectedModel("ERR-666/69.420/04/2022", 1))
                add(4, DOCSelectedModel("ERR-666/69.420/05/2022", 0))
                add(5, DOCSelectedModel("ERR-666/69.420/06/2022", 1))
            }
            val listAdapter = object : RecyclerView.Adapter<ViewHolder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                    ViewHolder(
                        LayoutInflater.from(requireContext()).inflate(R.layout.component_doc, parent, false)
                    )


                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    val model = mockModel[position]

                    holder.itemView.apply {
                        findViewById<TextView>(R.id.doc_title).text = model.getTitle()
                        findViewById<SeekBar>(R.id.doc_seekbar).apply {
                            isEnabled = false
                            progress = model.getProgress()
                        }
                        if (model.getProgress() == 2) {
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
                        setOnClickListener {
                            if (viewModel.selectingItemList.value == true)
                                multipleSelect(model, holder)
                            else
                                Snackbar.make(view, "Go to detailed with date", Snackbar.LENGTH_LONG).show()
                        }
                        setOnLongClickListener {
                            multipleSelect(model, holder)

                            return@setOnLongClickListener true
                        }
                    }
                }
                override fun getItemCount(): Int = 6
            }
            docRecycler.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }
            docSearch.apply {
                KeyboardVisibilityEvent.setEventListener(
                    requireActivity()
                ) {
                    if (!it) this.clearFocus()
                }

                setOnQueryTextListener(
                    object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            KeyboardUtils.hide(requireActivity(), binding.root)
                            docSearch.clearFocus()

                            val submittedText = p0
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
                            return true
                        }
                    }
                )
            }
            docFilter.apply {
                val mockModel = mutableListOf<DOCFilter>().apply {
                    add(DOCFilter("All Status"))
                    add(DOCFilter("Done (1)"))
                    add(DOCFilter("In Progress (5)"))
                }
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = object : RecyclerView.Adapter<ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                            LayoutInflater.from(requireContext())
                                .inflate(R.layout.component_filter, parent, false)
                        )


                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val model = mockModel[position]

                        holder.itemView.apply {
                            findViewById<TextView>(R.id.filter_text).apply {
                                text  = model.getStatus()
                                viewModel.selected.observe(viewLifecycleOwner){
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
                                }

                                setOnClickListener {
                                    viewModel.setSelected(position)
                                }
                            }
                        }
                    }

                    override fun getItemCount(): Int = 3
                }
            }
        }
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
}

class ViewHolder(view:View): RecyclerView.ViewHolder(view)
