package com.telkom.capex.login.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telkom.capex.login.fragments.LoginFragment

class LoginAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return LoginFragment().apply {
            arguments = Bundle().apply {
                putInt("pos", position)
            }
        }
    }
}