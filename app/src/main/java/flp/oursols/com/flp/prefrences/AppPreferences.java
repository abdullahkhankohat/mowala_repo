package flp.oursols.com.flp.prefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import flp.oursols.com.flp.models.UserFeaturesUsingSms;

/**
 * Created by Anwar on 8/12/2016.
 */
public class AppPreferences {

    public static void saveAdminAccess(Context context, boolean isAdmin) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(PreferencesKeys.ADMIN_ACcESS, isAdmin);
        ed.commit();
    }

    public static boolean isAdminAccess(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PreferencesKeys.ADMIN_ACcESS, false);
    }


    public static void saveUser(Context context, UserFeaturesUsingSms user) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(PreferencesKeys.USER, new Gson().toJson(user));
        ed.commit();
    }
    public static UserFeaturesUsingSms getUser(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String data =sp.getString(PreferencesKeys.USER, StringUtils.EMPTY) ;
        UserFeaturesUsingSms user = null;
        if(StringUtils.isBlank(data)){
            user = new UserFeaturesUsingSms();
        } else {
            user = new Gson().fromJson(data, UserFeaturesUsingSms.class);
        }
        return user;
    }

    public static void saveLocation(Context context, LatLng location) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        if (location.latitude != 0.0 && location.longitude != 0.0) {
            ed.putString(PreferencesKeys.LATITUDE, location.latitude + "");
            ed.putString(PreferencesKeys.LONGITUDE, location.longitude + "");
        }
        ed.commit();
    }


    public static double getLatitude(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sp.getString(PreferencesKeys.LATITUDE, "0.0"));
    }

    public static double getLongitude(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sp.getString(PreferencesKeys.LONGITUDE, "0.0"));
    }


}
