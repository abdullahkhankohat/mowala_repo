package flp.oursols.com.flp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

@TargetApi(23)
public class Permissions {

    public static boolean hasCameraPermissions(Context context) {
        String permission = "android.permission.CAMERA";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void getCameraPermissions(Context context){
        String[] permissions = { "android.permission.CAMERA" };
        ((Activity)context).requestPermissions(permissions, 11);
    }

    public static boolean hasLocationPermissions(Context context) {
        String permission1 = "android.permission.ACCESS_FINE_LOCATION";
        String permission2 = "android.permission.ACCESS_COARSE_LOCATION";
        int res1 = context.checkCallingOrSelfPermission(permission1);
        int res2 = context.checkCallingOrSelfPermission(permission2);
        return (res1 == PackageManager.PERMISSION_GRANTED ||
                res2 == PackageManager.PERMISSION_GRANTED);
    }

    public static void getLocationPermissions(Context context){
        String[] permissions = { "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION" };
        ((Activity)context).requestPermissions(permissions, 12);
    }

    public static boolean hasContactsPermissions(Context context) {
        String permission = "android.permission.GET_ACCOUNTS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void getContactsPermissions(Context context){
        String[] permissions = { "android.permission.GET_ACCOUNTS" };
        ((Activity)context).requestPermissions(permissions, 14);
    }

    public static boolean hasStoragePermissions(Context context) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void getStoragePermissions(Context context){
        String[] permissions = { "android.permission.WRITE_EXTERNAL_STORAGE" };
        ((Activity)context).requestPermissions(permissions, 13);
    }

    public static void getPhotosPermissions(Context context){
        String[] permissions = { "android.permission.CAMERA",
                "android.permission.WRITE_EXTERNAL_STORAGE" };
        ((Activity)context).requestPermissions(permissions, 11);
    }

    public static boolean hasPermission(Context context, String permission){
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }

    public static boolean hasCallPermission(Context context) {
        return (context.checkCallingOrSelfPermission("android.permission.CALL_PHONE") ==
                PackageManager.PERMISSION_GRANTED);
    }

    public static void getCallPermission(Context context) {
        String[] permissions = {"android.permission.CALL_PHONE"};
        ((Activity) context).requestPermissions(permissions, 10);
    }

    public static boolean hasMicPermission(Context context) {
        return (context.checkCallingOrSelfPermission("android.permission.RECORD_AUDIO") ==
                PackageManager.PERMISSION_GRANTED) &&
                (context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") ==
                        PackageManager.PERMISSION_GRANTED);
    }

    public static void getMicPermission(Context context) {
        String[] permissions = {"android.permission.RECORD_AUDIO",
                "android.permission.WRITE_EXTERNAL_STORAGE"};
        ((Activity) context).requestPermissions(permissions, 15);
    }
}