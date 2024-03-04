package com.example.merchantdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_INVOICE_COUNT = "invoiceCount";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Username and Password methods

    public static void saveCredentials(Context context, String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public static String getUsername(Context context) {
        return getSharedPreferences(context).getString(KEY_USERNAME, null);
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context).getString(KEY_PASSWORD, null);
    }

    // Invoice count methods

    public static void incrementInvoiceCount(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        int invoiceCount = sharedPreferences.getInt(KEY_INVOICE_COUNT, 1);
        invoiceCount++;
        sharedPreferences.edit().putInt(KEY_INVOICE_COUNT, invoiceCount).apply();
    }

    public static int getInvoiceCount(Context context) {
        return getSharedPreferences(context).getInt(KEY_INVOICE_COUNT, 1);
    }
}
