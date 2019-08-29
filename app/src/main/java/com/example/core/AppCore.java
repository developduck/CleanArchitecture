package com.example.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.core.manage.Debugger;
import com.example.core.manage.ExceptionHandler;
import com.example.core.manage.Logger;

/**
 * Created by Hwang on 2016-10-15.
 * 작성자 : 황의택
 * 내용 : 예외 관리, 공통 데이터 관리 담당
 */
public class AppCore extends Application implements LifecycleObserver {
    private static AppCore appCore = null;

    private Point point = new Point();
    private float density;

    public AppCore() {
        super();
    }
    public synchronized static AppCore getApplication() {
        return appCore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();

        listenForForeground();
    }
    public void initApplication() {
        //어플리케이션 생명 주기
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        //디버거 초기화
        Debugger.initialize(this);
        //로거 초기화
        Logger.initialize(this);
        //전역 예외 핸들러 선언
        new ExceptionHandler();
        //Application 싱글톤 저장
        AppCore.appCore = this;
        //전역 정보
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getSize(point);
        display.getMetrics(outMetrics);
        density = outMetrics.density;
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void listenForForeground() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) { appCore.onActivityCreated(activity, savedInstanceState); }
            @Override
            public void onActivityStarted(Activity activity) { appCore.onActivityStarted(activity); }
            @Override
            public void onActivityResumed(Activity activity) { appCore.onActivityResumed(activity); }
            @Override
            public void onActivityPaused(Activity activity) { appCore.onActivityPaused(activity); }
            @Override
            public void onActivityStopped(Activity activity) { appCore.onActivityStopped(activity); }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) { appCore.onActivitySaveInstanceState(activity, outState); }
            @Override
            public void onActivityDestroyed(Activity activity) { appCore.onActivityDestroyed(activity); }
        });
    }

    protected void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
    protected void onActivityStarted(Activity activity) {}
    protected void onActivityResumed(Activity activity) {}
    protected void onActivityPaused(Activity activity) {}
    protected void onActivityStopped(Activity activity) {}
    protected void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
    protected void onActivityDestroyed(Activity activity) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void notifyForeground() {
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void notifyBackground() {
    }

    public static int getScreenWidth() {
        return appCore.point.x;
    }
    public static int getScreenHeight() {
        return appCore.point.y;
    }
    public static float getDensity() { return appCore.density; }
}
