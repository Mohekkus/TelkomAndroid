package com.telkom.capex.ui.menu.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.telkom.capex.R
import com.telkom.capex.databinding.FragmentTrackerBinding
import com.telkom.capex.ui.menu.tracker.fragments.BOPTrackerFragment
import com.telkom.capex.ui.menu.tracker.fragments.DOCTrackerFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class TrackerFragment : Fragment() {

    private var _binding: FragmentTrackerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by hiltNavGraphViewModels<TrackerViewModel>(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bopPager.apply {
                isUserInputEnabled = false
                adapter = object : FragmentStateAdapter(this@TrackerFragment) {
                    override fun getItemCount(): Int = 1

                    override fun createFragment(position: Int): Fragment {
                        return BOPTrackerFragment()
//                        return when(position) {
//                            0 -> DOCTrackerFragment()
//                            else -> BOPTrackerFragment()
//                        }
                    }
                }
            }
//            TabLayoutMediator(
//                budgetTab,
//                budgetPager
//            ) { tab, position ->
//                tab.text = when(position) {
//                    0 -> "DOC"
//                    1 -> "BOP"
//                    else -> "sum thin u doin"
//                }
//            }.attach()

            bopButtonAdd.apply {
                viewModel.selectingItemList.observe(viewLifecycleOwner) {
                    if (it) {
                        setImageResource(R.drawable.ic_baseline_edit_24)
                        setOnClickListener {
                            Snackbar.make(binding.root, "Edit ${viewModel.getCountSelectedItemList()} Item", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        setImageResource(R.drawable.ic_baseline_add_24)
                        setOnClickListener {
                            Snackbar.make(binding.root, "Add Item", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}