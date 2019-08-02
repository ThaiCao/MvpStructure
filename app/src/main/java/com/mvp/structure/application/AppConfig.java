package com.mvp.structure.application;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class AppConfig {
    public final static String APP_CONFIG = "appConfig";
    // image cache path
    public static final String IMAGE_CACHE = AppApplication.context().getCacheDir().getPath() + File.separator + "image-cache";

    // data cache path
    public static final String DATA_CACHE = AppApplication.context().getCacheDir().getPath() + File.separator + "data-cache";

    //image cache maxsize
    // public final static int IMAGE_CACHE_MAXSIZE = 20 * 1024 * 1024;

    // webview cache path
    public final static String WEB_CACHE_PATH = AppApplication.context().getCacheDir().getPath() + File.separator + "webCache";


    private final static String K_NIGHT_MODE = "night_mode";

    private static SharedPreferences sPref;

    /**
     * clear webview cache
     */
    public static void clearWebViewCache() {
//        FileUtil.deleteFile(WEB_CACHE_PATH);
    }

    private static SharedPreferences getPreferences() {
        if (sPref == null) {
            sPref = AppApplication.context().getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
        }
        return sPref;
    }

    public static boolean isNightMode() {
        return getPreferences().getBoolean(K_NIGHT_MODE, false);
    }

    public static void setNightMode(boolean isNightMode) {
        getPreferences()
                .edit()
                .putBoolean(K_NIGHT_MODE, isNightMode)
                .apply();
    }
}
