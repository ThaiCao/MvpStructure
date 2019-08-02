package com.mvp.structure.application;

import android.content.Context;
import android.os.Handler;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class AppApplication extends MultiDexApplication {

    private static AppApplication sContext = null;
    public static volatile Handler applicationHandler = null;

    public static long sStartTime = System.currentTimeMillis();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        sContext = this;
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        //Initialize the memory leak detector
//        LeakCanary.install(this);
        //init AppManager
        AppManager.init(this);
        applicationHandler = new Handler(context().getMainLooper());
        initTheme();
        initRealm();
    }

    private void initTheme(){
        //Set the app's theme to display based on time
        if(AppConfig.isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    private void initRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("app.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static AppApplication context() {
        return sContext;
    }


}
