package com.test.homework.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")
    val userId: String,

    @SerializedName("userName")
    val userName: String
)