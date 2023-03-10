package com.telkom.capex.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.login.data.repo.LoginRepository
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.login.data.model.AuthTokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepository
) : ViewModel() {


    private val _token = MutableLiveData<ServiceHandler<AuthTokenResponse>>()
    val token : LiveData<ServiceHandler<AuthTokenResponse>>
    get() = _token

    init {
        viewModelScope.launch {
            repo.getToken().let {
                _token.postValue(ServiceHandler.loading(null))
                if (it.isSuccessful) {
                    _token.postValue(ServiceHandler.success(it.body()))
                } else {
                    _token.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                }
            }
        }
    }

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