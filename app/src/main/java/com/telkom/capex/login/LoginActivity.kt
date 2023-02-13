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
                viewModel.loginProgress.observe(this@LoginActivity) {
                    setCurrentItem(it, true)
                }
            }
            bNext.setOnClickListener {
                //DOCTrackerFragment
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
                bLogin.setOnClickListener {
                    loginProgress.value?.let { value -> doCheck(value) }
                }
            }
        }
    }

    private fun doCheck(value: Int) {
            viewModel.apply {
                when (value) {
                    0 ->
                        if (username.value.isNullOrEmpty()) return else setProgress(value + 1)
                    1 ->
                        if(password.value.isNullOrEmpty()) return else setProgress(value + 1)
                    else -> attemptLogin()
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