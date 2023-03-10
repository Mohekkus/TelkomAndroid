package com.telkom.capex.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import com.telkom.capex.ContainerActivity
import com.telkom.capex.data.utility.Status
import com.telkom.capex.databinding.LayoutLoginBinding
import com.telkom.capex.login.ui.adapter.LoginAdapter
import com.telkom.capex.menu.tracker.fragments.DOCTrackerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject lateinit var sharedPreferences: SharedPreferences
    private val viewModel: LoginViewModel by viewModels()

    lateinit var binding: LayoutLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        LayoutLoginBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(binding.root)
        }

        viewModel.token.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { _token ->
//                        if (sharedPreferences.contains("token") && sharedPreferences.getString("token", "") != _token.toString())
                        sharedPreferences.edit()
                            .putString("token", _token.toString())
                            .apply()

                        Snackbar.make(binding.root, _token?.token.toString(), Snackbar.LENGTH_SHORT)
                            .show()
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
                    0 -> if (username.value.isNullOrEmpty()) return else setProgress(value + 1)
                    1 -> if (password.value.isNullOrEmpty()) return else attemptLogin()
                    else -> Snackbar.make(binding.root, "Something went wrong: Restart app, to continue", Snackbar.LENGTH_SHORT).show()
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