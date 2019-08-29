package com.example.cleanarchitecture;

import android.app.Activity;

import com.example.cleanarchitecture.di.component.ApplicationComponent;
import com.example.cleanarchitecture.di.component.DaggerApplicationComponent;
import com.example.cleanarchitecture.di.module.ApplicationModule;
import com.example.core.AppCore;
import com.example.core.manage.Logger;

/**
 * Created by Hwang on 2018-03-15.
 *
 * Description :
 */
public class CleanApp extends AppCore {
    private ApplicationComponent applicationComponent;

//    private void initializeInjector() {
//        this.applicationComponent = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule(this))
//                .build();
//    }
//    public ApplicationComponent getApplicationComponent() {
//        return this.applicationComponent;
//    }
    @Override
    public void onCreate() {
        super.onCreate();
//        this.initializeInjector();
    }
    @Override
    protected void notifyForeground() {
        Logger.d("notifyForeground");
    }
    @Override
    protected void notifyBackground() {
        Logger.d("notifyBackground");
    }
}
