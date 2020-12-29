package com.test.homework.ui.main.view;

import android.app.Application;

import com.test.homework.data.api.ApiComponent;
import com.test.homework.data.api.ApiModule;
import com.test.homework.data.api.AppModule;
import com.test.homework.data.api.DaggerApiComponent;

import static com.test.homework.BuildConfig.BASE_URL;

public class MyApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(BASE_URL))
                .build();
    }


    public ApiComponent getNetComponent() {
        return mApiComponent;
    }

}
