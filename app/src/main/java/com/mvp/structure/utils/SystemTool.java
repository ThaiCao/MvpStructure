/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mvp.structure.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by thai.cao on 8/1/2019.
 * @author kymjs (https://github.com/kymjs)
 * @version 1.1
 */
@SuppressLint("SimpleDateFormat")
public final class SystemTool {

    private static final String TAG ="SystemTool";
    /**
     * The specified format returns the current system time
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * Returns the current system time (formatted in HH:mm format)
     */
    public static String getDataTime() {
        return getDataTime("HH:mm");
    }

    /**
     * Returns the current system time (format is in the form yyyy_MM_dd_HH_mm_ss)
     */
    public static String getDataDetailedTime() {
        return getDataTime("yyyy_MM_dd_HH_mm_ss");
    }

    /**
     * Get the phone IMEI code
     */
    public static String getPhoneIMEI(Context cxt) {
        String phoneIMEI = null;
        try {
            TelephonyManager tm = (TelephonyManager) cxt
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(cxt, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            phoneIMEI = tm.getDeviceId();
        } catch (Exception e) { //When storage permissions are not granted
            e.printStackTrace();
            phoneIMEI = "";
        }

        return phoneIMEI;
    }

    /**
     * Get WLAN MAC Address unique id
     */
    public static String getMACAddress(Context cxt) {
        String MACAddress = null;
        try {
            WifiManager wm = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
            MACAddress = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) { //When android.permission.ACCESS_WIFI_STATE is not granted
            MACAddress="";
        }
        return MACAddress;
    }

    /**
     * Get the unique identifier of the device
     */
    public static String getSoleDeviceId(Context cxt) {
        return hexdigest((getPhoneIMEI(cxt)+getMACAddress(cxt)).getBytes());
    }

    /**
     * Get the mobile phone system SDK version
     *
     * @return API such as 17
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * Get the system version
     *
     * @return such as 2.3.3
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * Get the phone model
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * Call the system to send a text message
     */
    public static void sendSMS(Context cxt, String smsBody) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        cxt.startActivity(intent);
    }

    /**
     * Determine if the network is connected
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// Whether the network is connected
    }


    /**
     * Determine whether it is wifi networking
     */
    public static boolean isWiFi(Context cxt) {
        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // Wifi status：ConnectivityManager.TYPE_WIFI
        // 3G status：ConnectivityManager.TYPE_MOBILE
        State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return State.CONNECTED == state;
    }


    /**
     * Method of determining if a service is running
     *
     * @param mContext
     * @param serviceName
     * is the package name + service class name (for example: net.loonggg.testbackstage.TestService)
     * @return true means running, false means the service is not running
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
    /**
     * Determine if the phone is handling sleep
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

    /**
     * Install apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setData(Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Get the version name of the current application
     */
    public static String getAppVersionName(Context context) {
        String version = "0";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            throw new RuntimeException(SystemTool.class.getName()
                    + "the application not found");
        }
        return version;
    }

    /**
     * Get the version number of the current application
     */
    public static int getAppVersionCode(Context context) {
        int version = 0;


        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException(SystemTool.class.getName()
                    + "the application not found");
        }
        return version;
    }

    /**
     * Back home, running in the background
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    /**
     * Get app signature
     *
     * @param context
     * @param pkgName
     */
    public static String getSign(Context context, String pkgName) {
        try {
            PackageInfo pis = context.getPackageManager().getPackageInfo(
                    pkgName, PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (NameNotFoundException e) {
            throw new RuntimeException(SystemTool.class.getName() + "the "
                    + pkgName + "'s application not found");
        }
    }

    /**
     * Convert the signature string to the required 32-bit signature
     */
    private static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Get the available memory size of the device
     *
     * @param cxt application context object context
     * @return current memory size
     */
    public static int getDeviceUsableMemory(Context cxt) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // Returns the available memory of the current system
        return (int) (mi.availMem / (1024 * 1024));
    }

    /**
     * Clean up background processes and services
     *
     * @param cxt application context object context
     * @return The number of cleaned up
     */
    public static int gc(Context cxt) {
        long i = getDeviceUsableMemory(cxt);
        int count = 0; // Number of processes cleaned up
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        // Get a list of running services
        List<RunningServiceInfo> serviceList = am.getRunningServices(100);
        if (serviceList != null)
            for (RunningServiceInfo service : serviceList) {
                if (service.pid == android.os.Process.myPid())
                    continue;
                try {
                    android.os.Process.killProcess(service.pid);
                    count++;
                } catch (Exception e) {
                    LogUtil.handleException(TAG,e);
                    continue;
                }
            }

        // Get a list of running processes
        List<RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        if (processList != null)
            for (RunningAppProcessInfo process : processList) {
                // 一Processes with values greater than RunningAppProcessInfo.IMPORTANCE_SERVICE are unused for a long time or empty processes.
                // 一Processes with a general value greater than RunningAppProcessInfo.IMPORTANCE_VISIBLE are non-visible processes, that is, running in the background.
                if (process.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    // pkgList Get the package name running under this process
                    String[] pkgList = process.pkgList;
                    for (String pkgName : pkgList) {
                        LogUtil.d(TAG,"======Killing package name：" + pkgName);
                        try {
                            am.killBackgroundProcesses(pkgName);
                            count++;
                        } catch (Exception e) { // Prevent accidents
                            LogUtil.handleException(TAG,e);
                            continue;
                        }
                    }
                }
            }
        LogUtil.d(TAG, "Cleaned up" + (getDeviceUsableMemory(cxt) - i) + "M memory");
        return count;
    }


}