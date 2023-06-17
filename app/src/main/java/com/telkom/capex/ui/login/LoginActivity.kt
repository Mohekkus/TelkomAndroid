package com.telkom.capex.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.telkom.capex.network.utility.Status
import com.telkom.capex.databinding.LayoutLoginBinding
import com.telkom.capex.ui.login.ui.adapter.LoginAdapter
import com.telkom.capex.ui.menu.ContainerActivity
import com.telkom.capex.ui.menu.tracker.fragments.DOCTrackerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject lateinit var sharedPreferences: SharedPreferences
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    lateinit var binding: LayoutLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        LayoutLoginBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(binding.root)
        }
        auth = Firebase.auth
        viewModel.token.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { _token ->
//                        if (sharedPreferences.contains("token") && sharedPreferences.getString("token", "") != _token.toString())
                        sharedPreferences.edit()
                            .putString("token", _token.toString())
                            .apply()

//                        Snackbar.make(binding.root, _token?.token.toString(), Snackbar.LENGTH_SHORT)
//                            .show()
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
            viewModel.apply {
                buttonLogin.setOnClickListener {
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
        viewModel.apply {
            auth.signInWithEmailAndPassword(username.value ?: "", password.value ?: "")
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser

                        toDashboard(true)
                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        toDashboard(true)
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            toDashboard(true)
        }
    }

    private fun toDashboard(condition: Boolean) {
        startActivity(
            Intent(
                this,
                ContainerActivity::class.java
            ).putExtra(
                "Login",
                condition
            )
        )
        finish()
    }
}