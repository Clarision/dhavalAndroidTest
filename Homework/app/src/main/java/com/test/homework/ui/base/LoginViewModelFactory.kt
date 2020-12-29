package com.test.homework.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.homework.data.ripository.LoginRepository
import com.test.homework.ui.main.viewmodel.LoginViewModel
import retrofit2.Retrofit


class LoginViewModelFactory(var context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(context,
                    loginRepository = LoginRepository(
                        context
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}