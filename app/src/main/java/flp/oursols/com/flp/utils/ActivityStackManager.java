package flp.oursols.com.flp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import flp.oursols.com.flp.screens.HomeScreen;
import flp.oursols.com.flp.screens.TrackingScreen;
import flp.oursols.com.flp.services.LocationService;

/**
 * Created by Anwar on 8/10/2016.
 */
public class ActivityStackManager {

    private static Context mContext;
    private static final ActivityStackManager mActivityStack = new ActivityStackManager();

    private ActivityStackManager() {
    }

    public static ActivityStackManager getInstance(Context context) {
        mContext = context;
        return mActivityStack;
    }

    public void startMainHomeScreen() {
        Intent intent = new Intent(mContext, HomeScreen.class);
        intent.putExtra("New", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ((Activity) mContext).startActivity(intent);
    }


    public void startMainTrackingScreen() {
        Intent intent = new Intent(mContext, TrackingScreen.class);
        intent.putExtra("New", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ((Activity) mContext).startActivity(intent);
    }


    public void startLocationService() {
        Intent intent = new Intent(mContext, LocationService.class);
        ((AppCompatActivity) mContext).startService(intent);
    }

}
