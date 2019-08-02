package com.mvp.structure.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by thai.cao on 8/1/2019.
 * Image loading class, unified adaptation (easy to change library, easy to manage)
 */

public class ImageLoader {


    /**
     * @param String @param string A file path, or a uri or url
     */
    public static void load(Context context, String String, ImageView view) {
        Glide.with(context)
                .load(String)
                .into(view);
    }



    public static void clear(Context context) {

        Glide.getPhotoCacheDir(context).delete();
    }
}
