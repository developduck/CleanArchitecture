package com.example.cleanarchitecture.di.component;

import android.content.Context;

import com.example.cleanarchitecture.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hwang on 2018-04-25.
 *
 * Description :
 */
@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
}
