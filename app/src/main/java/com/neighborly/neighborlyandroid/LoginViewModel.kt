package com.neighborly.neighborlyandroid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neighborly.neighborlyandroid.models.LoginRequest


class LoginViewModel: ViewModel()  {
    private val _repository: LoginRepository = LoginRepository()

    val userIsAuthorized: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun onLoginButtonPress(email:String, password:String ) {
       val request: LoginRequest = LoginRequest(email,password)
        _repository.makeRequest(request)

    }

}
