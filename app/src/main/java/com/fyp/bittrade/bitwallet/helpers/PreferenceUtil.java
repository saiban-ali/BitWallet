package com.fyp.bittrade.bitwallet.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    public PreferenceUtil() {
    }

    public static boolean saveWalletId(String walletId, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("walletId", walletId);

        editor.apply();

        return true;
    }

    public static String getWalletId(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String str = preferences.getString("walletId", "");

        return preferences.getString("walletId", "");
    }
}
