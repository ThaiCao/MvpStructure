package com.mvp.structure.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by thai.cao on 8/1/2019.
 */

public class LogUtil {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private LogUtil() {
        //no instance
    }

    public static void d(@NonNull String TAG, @Nullable String message) {
        Log.d(TAG, message);
    }

    public static void e(@NonNull String TAG, @Nullable String message) {
        Log.e(TAG, message);
    }

    public static void i(@NonNull String TAG, @Nullable String message) {
        Log.i(TAG, message);
    }

    public static void v(@NonNull String TAG, @Nullable String message) {
        Log.v(TAG, message);
    }

    public static void w(@NonNull String TAG, @Nullable String message) {
        Log.w(TAG, message);
    }

    public static void wtf(@NonNull String TAG, @Nullable String message) {
        Log.wtf(TAG, message);
    }

    public static void handleException(String TAG, final Throwable ex) {
        //Send an error log with Union
        LogUtil.e(TAG, ex.getMessage());
//        LogUtil.e(TAG, getCrashReport(AppApplication.context(),ex));
    }

    private static String getCrashReport(Context context, Throwable ex) {

        StringBuilder exceptionStr = new StringBuilder();
        PackageInfo pinfo;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            exceptionStr
                    .append("Version: ")
                    .append(pinfo.versionName)
                    .append("\nVersionCode: ")
                    .append(pinfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            exceptionStr.append("\nthe application not found \n");
        }
        exceptionStr
                .append("\nAndroid: ")
                .append(android.os.Build.VERSION.RELEASE)
                .append("(")
                .append(android.os.Build.MODEL)
                .append(")\n");
        String stackTraceString = Log.getStackTraceString(ex);
        if (stackTraceString != null && stackTraceString.length() > 0) {
            exceptionStr
                    .append(stackTraceString);
        } else {
            exceptionStr
                    .append("\nException: ")
                    .append(ex.getMessage())
                    .append("\n");
            StackTraceElement[] elements = ex.getStackTrace();
            for (StackTraceElement element : elements) {
                exceptionStr
                        .append(element.toString());
            }
        }


        return exceptionStr.toString();
    }


}