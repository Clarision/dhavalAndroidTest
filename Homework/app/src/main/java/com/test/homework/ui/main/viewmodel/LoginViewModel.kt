package com.test.homework.ui.main.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.homework.R
import com.test.homework.data.api.Api
import com.test.homework.data.database.User
import com.test.homework.data.model.*
import com.test.homework.data.ripository.LoginRepository
import com.test.homework.util.CommonSingleton
import com.test.homework.util.CommonSingleton.getDeviceImei
import com.test.homework.util.CommonSingleton.getDeviceImsi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginViewModel(var context: Context, private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String, retrofit: Retrofit) {


        var loginResponse: LoginResponse? = null
        if (!CommonSingleton.isNetworkStatusAvialable(context)) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.no_internet),
                Toast.LENGTH_SHORT
            ).show()

        } else {

            var IMSI = getDeviceImsi(context)
            var IMEI = getDeviceImei(context)

            if (IMSI.isEmpty())
                IMSI = "357175048449937"

            if (IMEI.isEmpty())
                IMEI = "510110406068589"


            var api = retrofit.create(Api::class.java)
            val call: Call<LoginResponse?> = api.login(
                IMSI, IMEI, username, password
            )!!
            call.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>?,
                    response: Response<LoginResponse?>
                ) {

                    if (response.isSuccessful) {
                        loginRepository.setLoggedInUser(response.body()!!)
                        loginResponse = response!!.body()
                        _loginResult.value =
                            LoginResult(success = LoggedInUserView(loginResponse!!))

                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {

                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            })
        }

    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }


    private fun isUserNameValid(username: String): Boolean {

        return username.isNotBlank()

    }


    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }


    fun logout(){
        loginRepository.setLogoutUser()
    }

    fun getLoginUserDetails() : User? {
        return loginRepository.getLoginUser()
    }

}