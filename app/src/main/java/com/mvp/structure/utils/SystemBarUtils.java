package com.mvp.structure.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
/**
 * Created by thai.cao on 8/1/2019.
   * Based on Android 4.4
   *
   * Description of main parameters:
   *
   * SYSTEM_UI_FLAG_FULLSCREEN : Hide StatusBar
   * SYSTEM_UI_FLAG_HIDE_NAVIGATION : Hide NavigationBar
   * SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN: The view expands to the position of the StatusBar and the StatusBar does not disappear.
   * Some processing is required here, generally the StatusBar is set to be fully transparent or translucent. You also need to use fitSystemWindows= to prevent the view from expanding to Status.
   * Bar above (will add a layer of View on the StatusBar, the View can be moved)
   * SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION: The view expands to the location of the NavigationBar
   * SYSTEM_UI_FLAG_LAYOUT_STABLE: Stabilization effect
   * SYSTEM_UI_FLAG_IMMERSIVE_STICKY: Guaranteed to click anywhere will not exit
   *
   * Can set special effects instructions:
   * 1. Full screen effects
   * 2. Full screen click does not exit special effects
   * 3. Note that when 19 <=sdk <=21, the transparent bar must be set via Window.
   */

public class SystemBarUtils {

    private static final int UNSTABLE_STATUS = View.SYSTEM_UI_FLAG_FULLSCREEN;
    private static final int UNSTABLE_NAV = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    private static final int STABLE_STATUS = View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private static final int STABLE_NAV = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private static final int EXPAND_STATUS = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    private static final int EXPAND_NAV = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


    //Set the hidden StatusBar (click anywhere to restore)
    public static void hideUnStableStatusBar(Activity activity){
        //App full screen, hide StatusBar
        setFlag(activity,UNSTABLE_STATUS);
    }

    public static void showUnStableStatusBar(Activity activity){
        clearFlag(activity,UNSTABLE_STATUS);
    }

    //Hide the NavigationBar (click anywhere to restore)
    public static void hideUnStableNavBar(Activity activity){
        setFlag(activity,UNSTABLE_NAV);
    }

    public static void showUnStableNavBar(Activity activity){
        clearFlag(activity,UNSTABLE_NAV);
    }

    public static void hideStableStatusBar(Activity activity){
        //App full screen, hide StatusBar
        setFlag(activity,STABLE_STATUS);
    }

    public static void showStableStatusBar(Activity activity){
        clearFlag(activity,STABLE_STATUS);
    }

    public static void hideStableNavBar(Activity activity){
        //App full screen, hide StatusBar
        setFlag(activity,STABLE_NAV);
    }

    public static void showStableNavBar(Activity activity){
        clearFlag(activity,STABLE_NAV);
    }

    /**
     * Expand view to StatusBar
     */
    public static void expandStatusBar(Activity activity){
        setFlag(activity, EXPAND_STATUS);
    }

    /**
     * Expand view to NavBar
     * @param activity
     */
    public static void expandNavBar(Activity activity){
        setFlag(activity, EXPAND_NAV);
    }

    public static void transparentStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= 21){
            expandStatusBar(activity);
            activity.getWindow()
                    .setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
        }
        else if (Build.VERSION.SDK_INT >= 19){
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | attrs.flags);
            activity.getWindow().setAttributes(attrs);
        }
    }

    public static void transparentNavBar(Activity activity){
        if (Build.VERSION.SDK_INT >= 21){
            expandNavBar(activity);
            //The following method is only available on sdk: 21 or above.
            activity.getWindow()
                    .setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
        }
    }

    public static void setFlag(Activity activity, int flag){
        if (Build.VERSION.SDK_INT >= 19){
            View decorView = activity.getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility() | flag;
            decorView.setSystemUiVisibility(option);
        }
    }

    //Cancel flag
    public static void clearFlag(Activity activity, int flag){
        if (Build.VERSION.SDK_INT >= 19){
            View decorView = activity.getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility() & (~flag);
            decorView.setSystemUiVisibility(option);
        }
    }

    public static void setToggleFlag(Activity activity, int option){
        if (Build.VERSION.SDK_INT >= 19){
            if (isFlagUsed(activity,option)){
                clearFlag(activity,option);
            }
            else {
                setFlag(activity,option);
            }
        }
    }

    /**
     * @param activity
     * @return Whether flag has been used
     */
    public static boolean isFlagUsed(Activity activity, int flag) {
        int currentFlag = activity.getWindow().getDecorView().getSystemUiVisibility();
        if((currentFlag & flag)
                == flag) {
            return true;
        }else {
            return false;
        }
    }
}
