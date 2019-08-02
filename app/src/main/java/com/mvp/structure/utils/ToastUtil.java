package com.mvp.structure.utils;

import android.widget.Toast;

import com.mvp.structure.application.AppApplication;

/**
 * Created by thai.cao on 8/1/2019.
 */
public class ToastUtil {
    private static Toast sToast = null;
    public static boolean isShow = true;


    public static void showToast( String msg) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppApplication.context(), msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showToast(int resId) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppApplication.context(), resId, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showToastLong( String msg) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppApplication.context(), msg, Toast.LENGTH_LONG);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showToastLong(int resId) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppApplication.context(), resId, Toast.LENGTH_LONG);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    // Mainly for the need to cancel the prompt at some point
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

}
