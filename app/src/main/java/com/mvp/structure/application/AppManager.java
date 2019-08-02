package com.mvp.structure.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mvp.structure.BuildConfig;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by thai.cao on 7/31/2019.
 * App manager, manage the life cycle of all activities
 */
public class AppManager {
    private static final boolean isShowLog = BuildConfig.DEBUG;

    private static final String TAG = "AppManager";

    private Application mApplication;

    @SuppressLint("StaticFieldLeak")
    private static AppManager sInstance;

    private Stack<Activity> mActivityStack;

    private int mStageActivityCount = 0; //Front activity number

    private AppManager(Application application) {
        mActivityStack = new Stack<>();
        mApplication = application;
        application.registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }


    public static AppManager init(Application application) {
        if (application == null) {
            throw new NullPointerException("You cannot start a init on a null Application");
        }
        if (sInstance == null) {
            sInstance = new AppManager(application);
        }
        return sInstance;
    }

    public static AppManager getInstance() {
        return sInstance;
    }

    /**
     * Get the specified Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (mActivityStack != null)
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }


    /**
     * Add Activity to the stack
     */
    private boolean addActivity(Activity activity) {
        return activity != null && mActivityStack.add(activity);
    }

    private boolean removeActivity(Activity activity) {
        return activity != null && mActivityStack.remove(activity);
    }

    /**
     * Get the current Activity (the last one in the stack)
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!mActivityStack.empty()) {
            activity = mActivityStack.lastElement();
        }
        return activity;
    }

    public Activity beforeActivity() {
        Activity activity = null;
        if (mActivityStack.size() > 1) {
            activity = mActivityStack.get(mActivityStack.size() - 2);
        }
        return activity;
    }

    public Activity beforeActivity(Activity activity) {
        Activity beforeActivity = null;
        int indexOf = mActivityStack.indexOf(activity);
        if (indexOf >= 1) {
            beforeActivity = mActivityStack.get(indexOf - 1);
        }
        return beforeActivity;
    }


    /**
     * End the current Activity (the last one in the stack)
     */
    public void finishActivity() {
        if (!mActivityStack.empty()) {
            finishActivity(mActivityStack.pop());
        }
    }

    /**
     * End the specified Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * End all activities of the specified class name
     */
    public void finishActivity(Class<?> cls) {
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * End all activities that specify a non-class name
     */
    public void finishOtherActivity(Class<?> cls) {
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }

    }

    /**
     * End all activities
     */
    public void finishAllActivity() {
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            iterator.remove();
            activity.finish();
        }
    }

    public Object[] getActivityArray(){
        return  mActivityStack.toArray();
    }

    /**
     * Determine if the app is in the foreground
     */
    public boolean isStagApp() {
        return mStageActivityCount > 0;
    }


    /**
     * Restart the application
     */
    public void resetApp() {
        Intent i = mApplication.getPackageManager()
                .getLaunchIntentForPackage(mApplication.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mApplication.startActivity(i);
        exit();
    }

    public void exit() {
        try {
            finishAllActivity();

            // Kill the application process
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }

    private class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            if (isShowLog) {
                Log.i(TAG, "onActivityCreated :" + activity);
            }
            AppManager.sInstance.addActivity(activity);

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (isShowLog) {
                Log.i(TAG, "onActivityStarted :" + activity);
            }
            mStageActivityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (isShowLog) {
                Log.i(TAG, "onActivityResumed :" + activity);
            }

        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (isShowLog) {
                Log.i(TAG, "onActivityPaused :" + activity);
            }

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (isShowLog) {
                Log.i(TAG, "onActivityStopped :" + activity);
            }
            mStageActivityCount--;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            if (isShowLog) {
                Log.i(TAG, "onActivitySaveInstanceState :" + activity);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (isShowLog) {
                Log.i(TAG, "onActivityDestroyed :" + activity);
            }
            AppManager.sInstance.removeActivity(activity);

        }
    }

}
