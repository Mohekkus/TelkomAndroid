package com.telkom.capex.login.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.telkom.capex.databinding.ComponentLoginPasswordBinding
import com.telkom.capex.login.LoginViewModel

class PasswordFragment: Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()
    lateinit var binding: ComponentLoginPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ComponentLoginPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etPassword.doAfterTextChanged {
            viewModel.setPassword(it.toString())
        }
    }
}