package com.telkom.capex.ui.menu

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.telkom.capex.R
import com.telkom.capex.databinding.ActivityMainBinding
import com.telkom.capex.etc.SupremeFragmentDirections
import com.telkom.capex.ui.menu.budget.viewmodel.BudgetSharedViewModel
import com.telkom.capex.ui.menu.tracker.TrackerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        if (intent.extras?.getBoolean("Login") == false) {
            binding.navView.visibility = View.GONE
            navController.apply {
                popBackStack()
                navigate(R.id.navigation_tracker)
            }
        } else {
            navController.apply {
                popBackStack()
                navigate(R.id.navigation_dashboard)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
