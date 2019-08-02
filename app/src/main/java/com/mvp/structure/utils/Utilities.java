package com.mvp.structure.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mvp.structure.application.AppApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by thai.cao on 8/1/2019.
 */
public class Utilities {

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final Object lock = new Object();
    public static float density = 1;


    public static void runOnUiThread(Runnable runnable) {
        synchronized (lock) {
            AppApplication.applicationHandler.post(runnable);
        }
    }

    public static void runDelayOnUiThread(Runnable runnable) {
        synchronized (lock) {
            AppApplication.applicationHandler.postDelayed(runnable, 2000);
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null)
            ((InputMethodManager) AppApplication.context()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(View view) {
        if (view != null)
            ((InputMethodManager) AppApplication.context()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static int getAge(Date dateOfBirth) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static byte[] readFile(File file) throws IOException {
        ByteArrayOutputStream output = null;
        InputStream input = null;
        try {
            byte[] buffer = new byte[4096];
            output = new ByteArrayOutputStream();
            input = new FileInputStream(file);
            int read = 0;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
            }
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
            }
        }
        return output.toByteArray();
    }

    public static double getDistanceByLatLon(double lat, double lon, double lat2, double lon2) {
        double R = 6371000;
        double fi_1 = Math.toRadians(lat);
        double fi_2 = Math.toRadians(lat2);
        double delta_fi = Math.toRadians(lat2 - lat);
        double delta_lambda = Math.toRadians(lon2 - lon);
        double a = Math.sin(delta_fi / 2) * Math.sin(delta_fi / 2) + Math.cos(fi_1) * Math.cos(fi_2) * Math.sin(delta_lambda / 2) * Math.sin(delta_lambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }

    public static int dp(int value) {
        return (int) (density * value);
    }

    public static class FontList {

        public static String SHARE_TECH_MONO = "ShareTechMono-Regular.ttf";
        public static String AMBASSADOR_PLUS_SANS_LIGHT = "AmbassadorPlusSans-Light.otf";
        public static String AMBASSADOR_PLUS_SANS_THIN = "AmbassadorPlusSans-Thin.otf";
        public static String AMBASSADOR_PLUS_SLAB_LIGHT = "AmbassadorPlusSlab-Light.otf";
        public static String AMBASSADOR_PLUS_SLAB_THIN = "AmbassadorPlusSlab-Thin.otf";
        public static String SFUI_BOLD = "SF UI Text Bold.otf";
        public static String SFUI_LIGHT = "SF UI Display Light.otf";
        public static String SFUI_REGULAR = "SF UI Text Regular.otf";
        public static String SFUI_SEMIBOLD = "SF UI Text Semibold.otf";
    }

    public static HashMap<String, Typeface> typefaces = new HashMap<String, Typeface>();

    public static void setFont(Object obj, String font_name) {

        Typeface font_face = typefaces.get(font_name);
        if (font_face == null) {
            font_face = Typeface.createFromAsset(AppApplication.context().getAssets(), "fonts/" + font_name);
            typefaces.put(font_name, font_face);
        }

        try {
            obj.getClass().getMethod("setTypeface", Typeface.class).invoke(obj, font_face);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isCardNotExpired(int expiryMonth, int expiryYear) {
        if (expiryMonth < 1 || 12 < expiryMonth) {
            return false;
        }

        Calendar now = Calendar.getInstance();
        int thisYear = now.get(Calendar.YEAR);
        int thisMonth = now.get(Calendar.MONTH) + 1;

        if (expiryYear < thisYear) {
            return false;
        }
        if (expiryYear == thisYear && expiryMonth < thisMonth) {
            return false;
        }
//        if (expiryYear > thisYear + CreditCard.EXPIRY_MAX_FUTURE_YEARS) {
//            return false;
//        }

        return true;
    }

    public static String serialize(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.encodeToString(baos.toByteArray(), 0);
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String sha1(String input) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(input.getBytes("UTF-8"));
            return byteArrayToHexString(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String join(Object[] pieces, String delimiter) {
        StringBuilder newString = new StringBuilder();
        int i = 0;
        for (Object string : pieces) {
            if (i > 0) {
                newString.append(delimiter);
            }
            newString.append(string);
            i++;
        }
        return newString.toString();
    }

    public static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void installApp(Context context, String appPackageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            } catch (Exception e1) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Double round2(Double val, RoundingMode roundingMode) {
        return new BigDecimal(val.toString()).setScale(2, roundingMode).doubleValue();
    }

    public static double parseDouble(final String text) {
        return parseDouble(text, null);
    }

    public static double parseDoubleLocaleUS(final String text) {
        return parseDouble(text, Locale.US);
    }

    public static double parseDouble(final String text, final Locale locale) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        final Locale numberLocale = locale != null ? locale : Locale.getDefault();
        final NumberFormat nf = NumberFormat.getInstance(numberLocale);

        double result = 0;
        try {
            result = nf.parse(text).doubleValue();
        } catch (final ParseException ex) {
            ex.printStackTrace();
            // Try with US locale.
            if (locale == null) {
                result = parseDouble(text, Locale.US);
            } else {
                result = 0;
            }
        }

        return result;
    }

    //<editor-fold desc="card 355">
    /*limit 5MB can run app*/
    public static boolean isInternalMemory(){
        String size = getAvailableInternalMemorySize();
        boolean result = false;
        if(size.contains("MB")){
            try{
                //-- Fix card #355-handle-memory-size
                long val = Long.valueOf(size.replace("MB", ""));
                //limit 5MB
                if(val >= 5){
                    result = true;
                }else {
                    result = false;
                }
            }catch (Exception e){
                Log.d(Utilities.class.getSimpleName(),"Error convert MB ");
                result = false;
            }
        }else if(size.contains("KB")){
            result = false;
        }
        return result;
    }

    private static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        String size = formatSize(availableBlocks * blockSize);
        Log.d(Utilities.class.getSimpleName(), "Internal Memory Size " + size);
        return size;
    }

    private static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        //-- Fix card #355-handle-memory-size
        /*int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }*/

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
    //</editor-fold>


    private static String getStrJSOnArray(String key, String data){
        String result = "";
        try {
            JSONObject messageJson = new JSONObject(data);
            if(messageJson.has(key)){
                JSONArray messageArr = messageJson.getJSONArray(key);
                if(messageArr != null && messageArr.length() > 0){
                    result = messageArr.getString(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //-- Fix card #296
    /** return value: .00 */
    public static String getStrDouble(double valDouble){
        DecimalFormatSymbols nf = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.00", nf);
        return df.format(valDouble);
    }

    public static void displayHtmlTextView(TextView tv, String message){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv.setText(Html.fromHtml(message));
        }
    }

    public static int getHeightScreen(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 17) {
            return defaultDisplay.getHeight() - getWidthScreen(context);
        }
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.y;
    }

    public static int getWidthScreen(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= 17) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            int i2 = displayMetrics.heightPixels;
            if (i2 > i) {
                return i2 - i;
            }
        }
        return 0;
    }

//    public static AlertDialog displayDialogConfirm(Context context, String message, String negativeLabel, DialogInterface.OnClickListener negativeListener, String positiveLabel, DialogInterface.OnClickListener positiveListener) {
//        AppAlertDialog dialog = new AppAlertDialog(context);
//        dialog.setTitle(context.getResources().getString(R.string.app_name));
//        dialog.setMessage(message);
//        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeLabel, negativeListener);
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveLabel, positiveListener);
//        dialog.show();
//        return dialog;
//    }
//
//    public static AlertDialog displayDialogOK(Context context, String message, String negativeLabel) {
//        AppAlertDialog dialog = new AppAlertDialog(context);
//        dialog.setTitle(context.getResources().getString(R.string.app_name));
//        dialog.setMessage(message);
//        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeLabel, (DialogInterface.OnClickListener) null);
//        dialog.show();
//        return dialog;
//    }
}
