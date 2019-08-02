package com.mvp.structure.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by thai.cao on 8/1/2019.
 */
public class BrightnessUtils {
    private static final String TAG = "BrightnessUtils";

    /**
     * Determine if automatic brightness adjustment is turned on
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean isAuto = false;
        try {
            isAuto = Settings.System.getInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e){
            e.printStackTrace();
        }
        return isAuto;
    }

    /**
     * Get the brightness of the screen
     * System brightness mode，The values of the system brightness obtained by the automatic mode and the manual mode are different.
     */
    public static int getScreenBrightness(Activity activity) {
        if(isAutoBrightness(activity)){
            return getAutoScreenBrightness(activity);
        }else{
            return getManualScreenBrightness(activity);
        }
    }

    /**
     * Get screen brightness in manual mode
     * @return value:0~255
     */
    public static int getManualScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * Get screen brightness in automatic mode
     * @return value:0~255
     */
    public static int getAutoScreenBrightness(Activity activity) {
        float nowBrightnessValue = 0;

        //Get the range of brightness under auto adjustment between 0~1
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getFloat(resolver, Settings.System.SCREEN_BRIGHTNESS);
            Log.d(TAG, "getAutoScreenBrightness: " + nowBrightnessValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Conversion range is (0~255)
        float fValue = nowBrightnessValue * 225.0f;
        Log.d(TAG,"brightness: " + fValue);
        return (int)fValue;
    }

    /**
     * Set brightness:By setting Windows 的 screenBrightness To modify the current Windows Brightness
     * lp.screenBrightness:The parameter range is 0~1
     */
    public static void setBrightness(Activity activity, int brightness) {
        try{
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            //will 0~255 Range of data，Convert to 0~1
            lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
            Log.d(TAG, "lp.screenBrightness == " + lp.screenBrightness);
            activity.getWindow().setAttributes(lp);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Get the brightness of the current system
     * @param activity
     */
    public static void setDefaultBrightness(Activity activity) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
            activity.getWindow().setAttributes(lp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

  /*  *//**
     * Get the brightness of the screen
     *
     * @param activity
     * @return
     *//*
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }*/
/*

    */
/**
     * Set brightness
     *
     * @param activity
     * @param brightness
     *//*

    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = (float) brightness * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }
*/

    /**
     * Setting the brightness follow system
     */
    public static void setUseSystemBrightness(Activity activity){
        WindowManager.LayoutParams lp =activity.getWindow().getAttributes();
        lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        activity.getWindow().setAttributes(lp);
    }
/*
    *//**
     * Determine if automatic brightness adjustment is turned on
     *
     * @return
     *//*
    public static boolean isAutoBrightness(Activity activity) {
        boolean autoBrightness = false;
        try {
            autoBrightness = Settings.System.getInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoBrightness;
    }*/

    /**
     * Stop automatic brightness adjustment
     *
     * @param activity
     */
    public static void stopAutoBrightness(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(activity)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return;
            }
        }
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * Turn on automatic brightness adjustment
     *
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(activity)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return;
            }
        }
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

}
