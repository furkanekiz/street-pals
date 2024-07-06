
package com.furkanekiz.streetpals.utils;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static final boolean DEBUG = false;


    private static void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }


    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            return new String(bytes);
        } catch (IOException e) {
            return null;
        }
    }







}
