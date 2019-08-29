package com.example.core.manage;

import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Hwang on 2016-10-15.
 * 작성자 : 황의택
 * 내용 : Application 범위에서 Exception을 관리하고 핸들링하는 클래스
 * 참고 : ACRA 라이브러리로 대체 가능
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    public ExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        try {
            StringWriter stackTrace = new StringWriter();
            ex.printStackTrace(new PrintWriter(stackTrace));
            String LINE_SEPARATOR = "\n";
            String errorContent = LINE_SEPARATOR +
                    "************ CAUSE OF ERROR ************" +
                    LINE_SEPARATOR +
                    stackTrace.toString() +
                    "************ CAUSE OF ERROR ************" +
                    LINE_SEPARATOR +
                    LINE_SEPARATOR +
                    "************ DEVICE INFORMATION ***********" +
                    LINE_SEPARATOR +
                    "Brand: " +
                    Build.BRAND +
                    LINE_SEPARATOR +
                    "Device: " +
                    Build.DEVICE +
                    LINE_SEPARATOR +
                    "Model: " +
                    Build.MODEL +
                    LINE_SEPARATOR +
                    "Id: " +
                    Build.ID +
                    LINE_SEPARATOR +
                    "Product: " +
                    Build.PRODUCT +
                    LINE_SEPARATOR +
                    "************ DEVICE INFORMATION ***********" +
                    LINE_SEPARATOR +
                    LINE_SEPARATOR +
                    "************ FIRMWARE OF ANDROID ************" +
                    LINE_SEPARATOR +
                    "SDK: " +
                    Build.VERSION.SDK_INT +
                    LINE_SEPARATOR +
                    "Release: " +
                    Build.VERSION.RELEASE +
                    LINE_SEPARATOR +
                    "Incremental: " +
                    Build.VERSION.INCREMENTAL +
                    LINE_SEPARATOR +
                    "************ FIRMWARE OF ANDROID ************" +
                    LINE_SEPARATOR;

            Logger.e(errorContent);
            System.exit(1);
        } catch (Exception e) {
            Logger.e("Exception Logger Failed!", e);
        }
    }
}
