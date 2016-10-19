package flp.oursols.com.flp.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.Subscribe;

import flp.oursols.com.flp.R;
import flp.oursols.com.flp.models.PlacesResult;
import flp.oursols.com.flp.prefrences.AppPreferences;
import flp.oursols.com.flp.prefrences.PreferencesKeys;
import flp.oursols.com.flp.utils.ActivityStackManager;
import flp.oursols.com.flp.utils.Constants;
import flp.oursols.com.flp.utils.Dialogs;
import flp.oursols.com.flp.utils.Utils;

public class HomeScreen extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    private FloatingActionButton fab1 , fab2, fab3, fab4;
    private LinearLayout fab2_layout,fab3_layout,fab4_layout;
    private TextView fab2_tv,fab3_tv,fab4_tv;
    private boolean animate = true;
    private Marker customMarker;
    private HomeScreen mCurrentActivity;
    private ActivityStackManager mStackManager;
    private boolean usingMyLocation = true;
    private static int SEARCH_PLACE = 201;
    private PlacesResult mPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initialize();

    }

    private void initialize() {
        mCurrentActivity=this;
        mStackManager = ActivityStackManager.getInstance(mCurrentActivity);
        fab1 = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);


        fab2_layout = (LinearLayout) findViewById(R.id.fab2_layout);
        fab3_layout = (LinearLayout) findViewById(R.id.fab3_layout);
        fab4_layout = (LinearLayout) findViewById(R.id.fab4_layout);

        fab2_tv = (TextView) findViewById(R.id.fab2_tv);
        fab3_tv = (TextView) findViewById(R.id.fab3_tv);
        fab4_tv = (TextView) findViewById(R.id.fab4_tv);


        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        animateCameraTo(AppPreferences.getLatitude(mCurrentActivity),AppPreferences.getLongitude(mCurrentActivity));
    }


    public void animateCameraTo(final double lat, final double lng) {
        if (mMap != null) {
            CameraPosition camPosition = mMap.getCameraPosition();
                mMap.getUiSettings().setScrollGesturesEnabled(false);
                addCircleAddMarker(lat,lng);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.
                        fromLatLngZoom(new LatLng(lat, lng), 16)), 1000, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        mMap.getUiSettings().setScrollGesturesEnabled(true);
                    }

                    @Override
                    public void onCancel() {
                        mMap.getUiSettings().setAllGesturesEnabled(true);
                    }
                });

        }

    }

    public void addCircleAddMarker(final double lat, final double lng){
        mMap.clear();
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker_layout, null);
        customMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng))
                .icon(BitmapDescriptorFactory.fromBitmap(Utils.createDrawableFromView(this, marker))));
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(200)
                .strokeColor(getResources().getColor(R.color.map_circle_boder))
                .fillColor(getResources().getColor(R.color.map_circle_bg)));
        circle.setCenter(new LatLng(lat,lng));
    }

    @Subscribe
    public void onEvent(String action) {
      if(action.equalsIgnoreCase(PreferencesKeys.LOCATION_UPDATED) && usingMyLocation){
         mMap.clear();
         animateCameraTo(AppPreferences.getLatitude(mCurrentActivity),AppPreferences.getLongitude(mCurrentActivity));
      }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (animate) {
                    animate = false;
                    setForwardAnimation();
                } else {
                    animate = true;
                    setReverseAnimation();
                }
                break;
            case R.id.fab2:
                fab2.animate().rotation(720).withLayer().setDuration(200);

                break;
            case R.id.fab3:
                fab3.animate().rotation(720).withLayer().setDuration(200);
                break;
            case R.id.fab4:
                fab4.animate().rotation(720).withLayer().setDuration(200);
                startPlacesActivity();
                break;
            case R.id.myLocationFab:
                fab4.animate().rotation(720).withLayer().setDuration(200);
                usingMyLocation=true;
                animateCameraTo(AppPreferences.getLatitude(mCurrentActivity),AppPreferences.getLongitude(mCurrentActivity));
                break;
            case  R.id.b1:
                if(!usingMyLocation){
                mStackManager.startMainTrackingScreen();
                }else{
                    Dialogs.INSTANCE.showToast(mCurrentActivity,"Select Destination");
                }
                break;

            case  R.id.placesBtn:
                break;
            case  R.id.settingsBtn:
                break;
        }
    }

    private void startPlacesActivity() {
        final Intent intent = new Intent(mCurrentActivity, PlacesMainActivity.class);
        intent.putExtra(Constants.PLACES_TITLE, "Pick Up Point");
        intent.putExtra(Constants.PLACES_TYPE, true);
        startActivityForResult(intent, SEARCH_PLACE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_PLACE) {
            if (resultCode == Activity.RESULT_OK) {
                animate = true;
                setReverseAnimation();
                mPlace = data.getParcelableExtra(Constants.PLACES_RESULT);
                usingMyLocation=false;
                animateCameraTo(mPlace.latitude, mPlace.longitude);
                AppPreferences.saveDestinationLocation(mCurrentActivity,new LatLng(mPlace.latitude,mPlace.longitude));
              //  setAddress(mPlace.name + "\n" + mPlace.address);
               // mIsGeoPlace = true;
            }
        }
    }


    @Override
    public void onBackPressed() {

        if (animate) {
            animate = false;
            super.onBackPressed();
        } else {
            animate = true;
            setReverseAnimation();
        }

    }

    private void setForwardAnimation() {
        fab2_tv.setVisibility(View.VISIBLE);
        fab3_tv.setVisibility(View.VISIBLE);
        fab4_tv.setVisibility(View.VISIBLE);

        fab2_layout.setVisibility(View.VISIBLE);
        fab3_layout.setVisibility(View.VISIBLE);
        fab4_layout.setVisibility(View.VISIBLE);

        fab2_layout.animate().withLayer().translationY(Utils.dpToPx(this,-200)).withLayer().setDuration(200);
        fab3_layout.animate().withLayer().translationY(Utils.dpToPx(this,-140)).withLayer().setDuration(200);
        fab4_layout.animate().withLayer().translationY(Utils.dpToPx(this,-80)).withLayer().setDuration(200);


        fab1.animate().withLayer().rotation(45);
        fab2.animate().withLayer().setDuration(200).rotation(360);
        fab3.animate().withLayer().setDuration(220).rotation(360);
        fab4.animate().withLayer().setDuration(250).rotation(360);
        findViewById(R.id.overlay).setVisibility(View.VISIBLE);
    }

    private void setReverseAnimation() {

        fab2_tv.setVisibility(View.GONE);
        fab3_tv.setVisibility(View.GONE);
        fab4_tv.setVisibility(View.GONE);



        setVisibilityGone();
        fab2_layout.animate().translationY(0).withLayer().setDuration(200);
        fab3_layout.animate().translationY(0).withLayer().setDuration(220);
        fab4_layout.animate().translationY(0).withLayer().setDuration(250);

        fab1.animate().withLayer().rotation(0);
        fab2.animate().setDuration(200).rotation(-360);
        fab3.animate().setDuration(220).rotation(-360);
        fab4.animate().setDuration(250).rotation(-360);
        findViewById(R.id.overlay).setVisibility(View.GONE);
    }

    private void setVisibilityGone()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                fab2_layout.setVisibility(View.GONE);
                fab3_layout.setVisibility(View.GONE);
                fab4_layout.setVisibility(View.GONE);
            }
        }, 220);
    }
}

