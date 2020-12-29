package com.test.homework.data.api

import com.test.homework.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Header("IMSI") IMSI: String?,
        @Header("IMEI") IMEI: String?,
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<LoginResponse?>?
    
}