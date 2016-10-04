package flp.oursols.com.flp.screens;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import flp.oursols.com.flp.R;
import flp.oursols.com.flp.utils.Utils;

public class HomeScreen extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    private FloatingActionButton fab1 , fab2, fab3, fab4;
    private LinearLayout fab2_layout,fab3_layout,fab4_layout;
    private TextView fab2_tv,fab3_tv,fab4_tv;
    private boolean animate = true;

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


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                break;

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

