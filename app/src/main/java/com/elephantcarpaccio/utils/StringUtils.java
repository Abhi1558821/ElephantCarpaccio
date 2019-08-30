package com.elephantcarpaccio.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility function for String manipulation
 */
public final class StringUtils {

    public static String readRawResource(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String read = reader.readLine();

        while (read != null) {
            sb.append(read);
            read = reader.readLine();
        }

        return sb.toString();
    }
}

