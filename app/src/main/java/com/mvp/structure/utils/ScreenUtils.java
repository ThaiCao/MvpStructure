package com.mvp.structure.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mvp.structure.application.AppApplication;

import java.lang.reflect.Method;


/**
 * Created by thai.cao on 8/1/2019.
 */

public class ScreenUtils {

    public static int dpToPx(int dp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,metrics);
    }

    public static int pxToDp(int px){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) (px / metrics.density);
    }

    public static int spToPx(int sp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,metrics);
    }

    public static int pxToSp(int px){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) (px / metrics.scaledDensity);
    }

    /**
     * Get the size of the app display app area（Head navigation bar+ActionBar+Root layout），Does not include virtual buttons
     * @return
     */
    public static int[] getAppSize(){
        int[] size = new int[2];
        DisplayMetrics metrics = getDisplayMetrics();
        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }

    /**
     * Get the size of the entire phone screen(Including virtual buttons)
     * Must be used after the onWindowFocus method
     * @param activity
     * @return
     */
    public static int[] getScreenSize(AppCompatActivity activity){
        int[] size = new int[2];
        View decorView = activity.getWindow().getDecorView();
        size[0] = decorView.getWidth();
        size[1] = decorView.getHeight();
        return size;
    }

    /**
     * Get the height of the navigation bar
     * @return
     */
    public static int getStatusBarHeight(){
        Resources resources = AppApplication.context().getResources();
        int resourceId = resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * Get the height of the virtual button
     * @return
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        Resources rs = AppApplication.context().getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar()) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * Whether there is a virtual button
     * @return
     */
    private static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = AppApplication.context().getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    public static DisplayMetrics getDisplayMetrics(){
        DisplayMetrics metrics = AppApplication.context()
                .getResources()
                .getDisplayMetrics();
        return metrics;
    }
}
