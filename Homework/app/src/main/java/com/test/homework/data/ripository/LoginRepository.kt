package com.test.homework.data.ripository

import android.content.Context
import com.test.homework.data.database.DatabaseClient
import com.test.homework.data.database.User
import com.test.homework.data.model.LoggedInUser
import com.test.homework.data.model.LoginResponse

class LoginRepository(
    var context: Context
) {

    var user: LoggedInUser? = null
        private set


    init {
        user = null
    }


    fun setLoggedInUser(loggedInUser: LoginResponse) {

        val user = User()
        user.user_name = loggedInUser.user.userName
        user.user_id = loggedInUser.user.userId
        DatabaseClient.getInstance(context).appDatabase.userDao().insert(user)
    }

    fun setLogoutUser() {
        DatabaseClient.getInstance(context).appDatabase.userDao().nukeTable()
    }

    fun getLoginUser(): User? {

        var userList = DatabaseClient.getInstance(context).appDatabase.userDao().all
        if (userList.size > 0)
            return DatabaseClient.getInstance(context).appDatabase.userDao().all.get(0)
        else
            return null
    }

}