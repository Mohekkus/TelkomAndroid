package com.telkom.capex.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.telkom.capex.ContainerActivity
import com.telkom.capex.databinding.LayoutLoginBinding
import com.telkom.capex.login.adapter.LoginAdapter
import com.telkom.capex.ui.tracker.fragments.DOCTrackerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    lateinit var binding: LayoutLoginBinding

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        LayoutLoginBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(binding.root)
        }
        initiateLogin()
    }

    private fun initiateLogin() {
        binding.apply {
            loginPager.apply {
                isUserInputEnabled = false
                adapter = LoginAdapter(this@LoginActivity)
            }
            bNext.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .add(binding.root.id, DOCTrackerFragment().apply {
                        arguments = Bundle().apply {
                            putBoolean("Guest", true)
                        }
                    })
                    .addToBackStack("Guest-Tracker")
                    .commit()
            }
            viewModel.apply {
                loginProgress.observe(this@LoginActivity) { value ->
                    bLogin.setOnClickListener {
                        if (value < 2) setProgress(value + 1)
                        else attemptLogin()
                    }
                }
            }
        }
    }

    private fun attemptLogin() {
        toDashboard()
    }

    private fun toDashboard() {
        startActivity(
            Intent(
                this,
                ContainerActivity::class.java
            ).putExtra(
                "login",
                false
            )
        )
        finish()
    }
}