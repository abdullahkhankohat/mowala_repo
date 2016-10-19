package flp.oursols.com.flp.prefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import flp.oursols.com.flp.models.Place;
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

    public static void setRecentPlaces(Context context, Place place){
        ArrayList<Place> recentPlaces = new ArrayList<>();
        recentPlaces.add(place);
        ArrayList<Place> prevPlaces = getRecentPlaces(context);
        if(prevPlaces != null && prevPlaces.size()> 0){
            //Remove place if already exists to avoid duplicate place
            Iterator<Place> iterator = prevPlaces.iterator();
            while (iterator.hasNext()) {
                Place temp = iterator.next();
                if(temp.name.equalsIgnoreCase(place.name)){
                    iterator.remove();
                }
            }
            //keep only 40 recent places, new places already added at top
            if (prevPlaces.size() > 39){
                prevPlaces.remove(39);
            }
            recentPlaces.addAll(prevPlaces);
        }
        String value = new Gson().toJson(recentPlaces, new TypeToken<ArrayList<Place>>(){}.getType());
        PreferenceManager.getDefaultSharedPreferences(context).
                edit().putString(PreferencesKeys.RECENT_PLACES, value).commit();
    }

    public static ArrayList<Place> getRecentPlaces(Context c){

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        String placesJson = sp.getString(PreferencesKeys.RECENT_PLACES, "");
        ArrayList<Place> places;
        places = new Gson().fromJson(placesJson, new TypeToken<ArrayList<Place>>(){}.getType());
        return places;
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


    public static void saveDestinationLocation(Context context, LatLng location) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        if (location.latitude != 0.0 && location.longitude != 0.0) {
            ed.putString(PreferencesKeys.LATITUDE_DEST, location.latitude + "");
            ed.putString(PreferencesKeys.LONGITUDE_DEST, location.longitude + "");
        }
        ed.commit();
    }


    public static double getDestLatitude(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sp.getString(PreferencesKeys.LATITUDE_DEST, "0.0"));
    }

    public static double getDestLongitude(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sp.getString(PreferencesKeys.LONGITUDE_DEST, "0.0"));
    }


}
