package com.telkom.capex.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _username = MutableLiveData<String>().apply {
        value = null
    }
    val username: LiveData<String> = _username
    fun setUsername(string: String) {
        _username.value = string
    }

    private val _password = MutableLiveData<String>().apply {
        value = null
    }
    val password: LiveData<String> = _password
    fun setPassword(string: String) {
        _password.value = string
    }

    private val _loginProgress = MutableLiveData(0)

    val loginProgress: LiveData<Int> = _loginProgress
    fun setProgress(number: Int) {
        _loginProgress.value = number
    }

}