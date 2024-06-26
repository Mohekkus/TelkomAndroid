package com.telkom.capex.ui.login.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.telkom.capex.ui.login.ui.fragments.EmailFragment
import com.telkom.capex.ui.login.ui.fragments.PasswordFragment

class LoginAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EmailFragment()
            else -> PasswordFragment()
        }
//        return LoginFragment().apply {
//            arguments = Bundle().apply {
//                putInt("pos", position)
//            }
//        }
    }
}