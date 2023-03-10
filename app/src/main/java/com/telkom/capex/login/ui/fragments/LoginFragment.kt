package com.telkom.capex.login.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.telkom.capex.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return when (arguments?.getInt("pos", 0)) {
            0 -> showLoginByEmail(inflater, container)
            else -> showLoginPassword(inflater, container)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {}
    }

    private fun showLoginByPhone(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.component_login_phone, container, false)
    }

    private fun showLoginByEmail(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.component_login_email, container, false)
    }

    private fun showLoginPassword(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.component_login_password, container, false)
    }
}