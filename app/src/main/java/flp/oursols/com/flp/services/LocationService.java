package flp.oursols.com.flp.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import org.greenrobot.eventbus.EventBus;
import flp.oursols.com.flp.prefrences.AppPreferences;
import flp.oursols.com.flp.prefrences.PreferencesKeys;

public class LocationService extends Service {

    private LocationManager mLocationManager;
    private LocationUpdatesListener mLocationUpdatesListener;
    private Context mContext;
    private EventBus mEventBus = EventBus.getDefault();


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationUpdatesListener = new LocationUpdatesListener();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, mLocationUpdatesListener);
            }else{
                mEventBus.post(PreferencesKeys.GPS_OFF);
            }
            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                        0, mLocationUpdatesListener);
            }

        }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(mLocationUpdatesListener);
        }

    }

    public class LocationUpdatesListener implements LocationListener {

        public void onLocationChanged(final Location location) {

            if (null != location) {
                AppPreferences.saveLocation(mContext, new LatLng(location.getLatitude(), location.getLongitude()));
                mEventBus.post(PreferencesKeys.LOCATION_UPDATED);
            }
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps not working",
                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

}
