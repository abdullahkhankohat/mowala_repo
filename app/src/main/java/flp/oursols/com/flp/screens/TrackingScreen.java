package flp.oursols.com.flp.screens;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import flp.oursols.com.flp.R;
import flp.oursols.com.flp.prefrences.AppPreferences;
import flp.oursols.com.flp.prefrences.PreferencesKeys;
import flp.oursols.com.flp.utils.Dialogs;
import flp.oursols.com.flp.utils.JSONParser;


public class TrackingScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker customMarker;
    LatLng startLocation,destLocation;
    TrackingScreen mCurrentActivity;
    TextView ETAtv,distanceTv;
    FloatingActionButton myLocationFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    private void init() {
        mCurrentActivity=this;
        ETAtv = (TextView) findViewById(R.id.ETAtv);
        distanceTv = (TextView) findViewById(R.id.distanceTv);
        myLocationFab = (FloatingActionButton) findViewById(R.id.myLocationFab);

        myLocationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateCameraTo(AppPreferences.getLatitude(mCurrentActivity),AppPreferences.getLongitude(mCurrentActivity),18);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMyLocationEnabled(true);
        DrawCompleteRoute();
    }

    public void addMemberMarkers(final double lat,final double lng){
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker_layout, null);
        TextView MarkerText = (TextView) marker.findViewById(R.id.num_txt);
      //  MarkerText.setBackgroundResource(R.drawable.ic_navigation);
        customMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng))
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyD4svI1DTicSeMI-9wT13UMz1CRoVLzXrU");
        return urlString.toString();
    }

    public void drawPath(String  result) {

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(50)
                    .color(Color.parseColor("#9233e1"))//Google maps blue color
                    .geodesic(true)
            );
        }
        catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {

        String url1;
        connectAsyncTask(String urlPass){
            url1 = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Dialogs.INSTANCE.showLoader(TrackingScreen.this);
        }
        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json1 = jParser.getJSONFromUrl(url1);
            return json1;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           Dialogs.INSTANCE.dismissDialog();
            if(result!=null){
                drawPath(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray array = jsonObject.getJSONArray("routes");

                    JSONObject routes = array.getJSONObject(0);

                    JSONArray legs = routes.getJSONArray("legs");

                    JSONObject steps = legs.getJSONObject(0);

                    JSONObject distance = steps.getJSONObject("distance");

                    JSONObject duration = steps.getJSONObject("duration");

                    String time =duration.getString("text");
                    Log.i("Distance", distance.toString());
                    Double dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );
                       // ETAtv.setText(distance.getString("text")+" distance and will take"+duration.getString("text") +" to reach there");

                    distanceTv.setText(distance.getString("text"));
                    ETAtv.setText(duration.getString("text"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Dialogs.INSTANCE.showToast(TrackingScreen.this,"Unable to draw route");
                }

              //  addMemberMarkers(startLocation.latitude,startLocation.longitude);
                animateCameraTo(startLocation.latitude,startLocation.longitude,18);


            }
        }
    }


    public void animateCameraTo(final double lat, final double lng, int zoom) {
        if (mMap != null) {

            CameraPosition camPosition = mMap.getCameraPosition();

                // isAnimating = true;
                mMap.getUiSettings().setScrollGesturesEnabled(false);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.
                        fromLatLngZoom(new LatLng(lat, lng), zoom)), 1000, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        mMap.getUiSettings().setScrollGesturesEnabled(true);
                        //  isAnimating = false;
                    }

                    @Override
                    public void onCancel() {
                        mMap.getUiSettings().setAllGesturesEnabled(true);

                    }
                });

        }

    }


    public void DrawCompleteRoute(){
        startLocation=new LatLng(AppPreferences.getLatitude(mCurrentActivity),AppPreferences.getLongitude(mCurrentActivity));
        destLocation = new LatLng(AppPreferences.getDestLatitude(mCurrentActivity),AppPreferences.getDestLongitude(mCurrentActivity));
        if(startLocation!=null && destLocation!=null) {
            String url = makeURL(startLocation.latitude, startLocation.longitude, destLocation.latitude, destLocation.longitude);
            connectAsyncTask connectAsyncTask = new connectAsyncTask(url);
            connectAsyncTask.execute();
        }else{
            Dialogs.INSTANCE.showToast(mCurrentActivity,"GPS off");
        }
    }


    @Subscribe
    public void onEvent(String action) {
        if(action.equalsIgnoreCase(PreferencesKeys.LOCATION_UPDATED)){
            animateCameraTo(AppPreferences.getLatitude(mCurrentActivity),AppPreferences.getLongitude(mCurrentActivity),18);
        }
    }


}
