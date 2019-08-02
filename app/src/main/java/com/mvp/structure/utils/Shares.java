package com.mvp.structure.utils;

import android.content.Context;
import android.content.Intent;

import com.mvp.structure.R;


/**
 * Created by thai.cao on 8/1/2019.
 */

public class Shares {
    public static void share(Context context, int stringRes) {
        share(context, context.getString(stringRes));
    }

    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.share)));
    }
}
