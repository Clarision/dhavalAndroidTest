package com.test.homework.data.api

import android.app.Application
import com.test.homework.ui.main.view.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule{
    private lateinit var mApplication: Application

    constructor(mApplication: Application){
        this.mApplication = mApplication
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }



}