package com.test.homework.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.PermissionRequest
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.test.homework.R
import com.test.homework.data.model.LoggedInUserView
import com.test.homework.ui.base.LoginViewModelFactory
import com.test.homework.ui.main.viewmodel.LoginViewModel
import com.test.homework.util.CommonSingleton
import retrofit2.Retrofit
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    lateinit var loginViewModel: LoginViewModel

    lateinit var username: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        (application as MyApplication).netComponent.inject(this)

        try {
            actionBar!!.hide();
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(this))
            .get(LoginViewModel::class.java)

        if(loginViewModel.getLoginUserDetails() != null){
            startActivity(Intent(this, DashboardActivity::class.java))
            this.finish()
        }


        getPermission()

        username = findViewById<EditText>(R.id.username)
        password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            when {
                loginState.usernameError != null -> {
                    username.error = getString(loginState.usernameError)
                    username.requestFocus()
                }
                loginState.passwordError != null -> {
                    password.error = getString(loginState.passwordError)
                    password.requestFocus()
                }
                else -> {

                    if (!CommonSingleton.isNetworkStatusAvialable(this)) {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.no_internet),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        loading.visibility = View.VISIBLE
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString(),
                            retrofit
                        )
                    }
                }
            }
        })


        loginViewModel.loginResult.observe(this@LoginActivity, Observer {

            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

        })



        password.apply {

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        loading.visibility = View.VISIBLE
                        loginViewModel.loginDataChanged(
                            username.text.toString(),
                            password.text.toString()
                        )
                    }
                }
                false
            }

            login.setOnClickListener {

                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }
        }
    }


    private fun getPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_PHONE_STATE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) { /* ... */
                    LoginActivity().finish();
                }


            }).check()
    }


    private fun updateUiWithUser(model: LoggedInUserView) {

        startActivity(Intent(this, DashboardActivity::class.java))
        this.finish()
    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}