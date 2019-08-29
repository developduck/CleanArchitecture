package com.example.cleanarchitecture.manage;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Hwang on 2018-03-23.
 *
 * Description :
 */
public class Runner {
    private static final int THREAD_COUNT = 3;

    private final Executor local;
    private final Executor main;

    @VisibleForTesting
    private Runner(Executor local, Executor main) {
        this.local = local;
        this.main = main;
    }
    public Runner() {
        this(Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor());
    }

    public Executor local() {
        return local;
    }
    public Executor main() {
        return main;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
