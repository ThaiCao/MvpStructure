package com.mvp.structure.utils;

/**
 * Created by thai.cao on 8/1/2019.
 */

public class Session {

    private static final String SHARED_COUNTER_READ = "counter_read";

    private static Session sInstance;
    public static Session getInstance(){
        if(sInstance == null){
            synchronized (Session.class){
                if (sInstance == null){
                    sInstance = new Session();
                }
            }
        }
        return sInstance;
    }

    public void saveCounterRead(int position){
        SharedPreUtils.getInstance().putInt(SHARED_COUNTER_READ, position);
    }


    public int getCounterRead(){
        int counter = SharedPreUtils.getInstance().getInt(SHARED_COUNTER_READ, 0);
        return counter;
    }
}
