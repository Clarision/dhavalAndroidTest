package com.test.homework.data.api

import com.test.homework.ui.main.view.DashboardActivity
import com.test.homework.ui.main.view.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
public interface ApiComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: DashboardActivity)
}