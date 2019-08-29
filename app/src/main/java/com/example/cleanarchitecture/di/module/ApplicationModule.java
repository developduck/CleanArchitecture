package com.example.cleanarchitecture.di.module;

import android.content.Context;

import com.example.cleanarchitecture.CleanApp;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Hwang on 2018-04-25.
 *
 * Description :
 */
@Module
public class ApplicationModule {
    private final CleanApp application;

    public ApplicationModule(CleanApp application) {
        this.application = application;
    }

    @Provides
    @Singleton Context provideApplicationContext(CleanApp context) {
        return this.application;
    }
}
