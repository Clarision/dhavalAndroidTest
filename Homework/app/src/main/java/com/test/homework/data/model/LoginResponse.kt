package com.test.homework.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("errorCode")
    val errorCode: String,

    @SerializedName("errorMessage")
    val errorMessage: String,

    @SerializedName("user")
    val user: User
)