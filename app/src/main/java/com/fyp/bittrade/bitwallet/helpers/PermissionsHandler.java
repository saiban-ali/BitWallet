package com.fyp.bittrade.bitwallet.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionsHandler {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_INTERNET = 2;
    private static final int REQUEST_CAMERA = 3;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_INTERNET = {
            Manifest.permission.INTERNET
    };

    private static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyCameraPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_CAMERA,
                    REQUEST_CAMERA
            );
        }
    }

    public static void verifyInternetPermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_INTERNET,
                    REQUEST_INTERNET
            );
        }
    }

}
