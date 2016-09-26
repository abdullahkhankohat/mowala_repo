package flp.oursols.com.flp.screens;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import flp.oursols.com.flp.R;

public class HomeScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        initialize();
        setFloatingMenu();
    }

    private void setFloatingMenu() {



        mFab.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFab.show(true);
                mFab.setShowAnimation(AnimationUtils.loadAnimation(HomeScreen.this, R.anim.show_from_bottom));
                mFab.setHideAnimation(AnimationUtils.loadAnimation(HomeScreen.this, R.anim.hide_to_bottom));
            }
        }, 300);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initialize() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
