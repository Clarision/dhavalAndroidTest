package com.test.homework.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.PermissionRequest
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
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


class DashboardActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        (application as MyApplication).netComponent.inject(this)

        try {
            actionBar!!.hide();
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val logout = findViewById<Button>(R.id.logout)
        val txtUserName = findViewById<TextView>(R.id.txtUserName)

        logout.setOnClickListener {
            showLogoutDialog()
        }

        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)


        try {
            val strUser = loginViewModel.getLoginUserDetails()!!.user_name
            if (!strUser.isNullOrEmpty())
                txtUserName.text = strUser
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun showLogoutDialog() {
        AlertDialog.Builder(this).setTitle(resources.getString(R.string.logout))
            .setMessage(resources.getString(R.string.msg_logout))
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                loginViewModel.logout();
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}