package com.telkom.capex.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.telkom.capex.ContainerActivity
import com.telkom.capex.databinding.FragmentTrackerBinding
import com.telkom.capex.databinding.LayoutLoginBinding
import com.telkom.capex.login.adapter.LoginAdapter
import com.telkom.capex.ui.tracker.fragments.DOCTrackerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    lateinit var binding: LayoutLoginBinding

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
//                supportFragmentManager.beginTransaction()
//                    .add(binding.root.id, DOCTrackerFragment())
//                    .commit()
            }
            bLogin.setOnClickListener {
                toDashboard()
            }
        }
    }

    private fun setPage(i: Int) {
        when (i) {
            1 -> setContent(0, "Email", android.R.drawable.ic_dialog_email)
            else -> setContent(1, "Phone", android.R.drawable.stat_sys_phone_call)
        }
    }

    private fun setContent(setItem: Int, setText: String, image: Int) {
        binding.apply {
            loginPager.currentItem = setItem
            tvLogOption.text = setText
            ivLogOption.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    image,
                    theme
                )
            )
        }
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