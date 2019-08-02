/*
 *  NetworkUtil.java
 *  Created on Sep 5, 2013
 */
package com.mvp.structure.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by thai.cao on 8/1/2019.
 */
public class NetworkUtil {
	
	/*
	 * References doc.
	 * http://www.gregbugaj.com/?p=47
	 */

    /**
     * check status wifi
     * 
     * @function name: boolean getNetworkStatus()
     * @param context
     * @return true if wifi turn on, else return false;
     */
    public static boolean isNetworkAvailable(Context context) {
    	
//    	System.out.println("Check Network state");
    	
//        ConnectivityManager connMgr = (ConnectivityManager) context.getApplicationContext()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = connMgr.getActiveNetworkInfo();
//
//        boolean flag = false;
//
//        if ((info != null && info.isConnected())) {
//			flag = true;
//			System.out.println("Network is available");
//		}
//        else{
//        	System.out.println("Network is disable");
//        }
//
//        return flag;

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfoMob = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo netInfoWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if ((netInfoMob != null && netInfoMob.isConnectedOrConnecting()) || (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
    }

    /**
     * check status wifi
     * 
     * @function name: boolean getNetworkStatus()
     * @param context
     * @return true if wifi turn on, else return false;
     */
    public static boolean getNetworkStatus(Context context) {

        boolean result = false;
        try {
            if (null == context) {
                return false;
            }

            ConnectivityManager connectionManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            
            // Modify
            NetworkInfo wimax = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

            if (wimax != null) {
                if (wimax.isAvailable()) {
                    if (wimax.isConnected()) {
                        wimax = null;
                        result = true;
                    }
                }
            }
            
            // Modify
            if (wifi.isAvailable()) {
                if (wifi.isConnected()) {
                    wifi = null;
                    result = true;
                } else {
                    if (mobile.isConnected()) {
                        mobile = null;
                        result = true;
                    }
                }
            } else if (mobile.isAvailable()) {
                if (mobile.isConnected()) {
                    mobile = null;
                    result = true;
                }
            }
            
        } catch (Exception e) {
            Log.e("NetWorkUtil", "getNetworkStatus error: " + e.getMessage());
            return false;
        }

        return result;
    }
    
    /**
    * Gets the state of Airplane Mode.
    * 
    * @param context
    * @return true if enabled.
    */
    @SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	public static boolean isAirplaneModeOn(Context context) {

		// if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
		// return Settings.System.getInt(context.getContentResolver(),
		// Settings.System.AIRPLANE_MODE_ON, 0) != 0;
		// } else {
		// return Settings.Global.getInt(context.getContentResolver(),
		// Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
		// }     
    	
    	return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    	
    }
    
}
