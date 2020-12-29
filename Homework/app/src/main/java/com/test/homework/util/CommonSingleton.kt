package com.test.homework.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager

object CommonSingleton {

    fun isNetworkStatusAvialable(context: Context): Boolean {
        try {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected)
                return true

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    @SuppressLint("MissingPermission")
    fun getDeviceImei(context: Context): String {

        var deviceid = ""

        try {
            val mTelephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            deviceid = mTelephonyManager!!.getDeviceId()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }finally {
            return deviceid
        }
    }


    @SuppressLint("MissingPermission")
    fun getDeviceImsi(context: Context): String {

        var deviceImsi = ""

        try {
            val mTelephonyMgr =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            deviceImsi = mTelephonyMgr!!.subscriberId as String
        } catch (ex: Exception) {
            ex.printStackTrace()
        }finally {
            return deviceImsi;
        }


    }

}