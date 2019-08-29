package com.example.cleanarchitecture.common.utils;

import android.os.Handler;

/**
 * Created by Hwang on 2018-03-28.
 *
 * Description :
 */
public class TestUtils {
    public static void delay(Runnable runner) {
        delay(runner, 3000);
    }
    public static void delay(Runnable runner, int millisecond) {
        new Thread(() -> new Handler().postDelayed(runner, millisecond)).start();
    }
}
