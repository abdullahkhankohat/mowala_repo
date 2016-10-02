package flp.oursols.com.flp.screens;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import flp.oursols.com.flp.R;

public class HomeScreen extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    FloatingActionButton fab1;
    Animation show_fab_1;
    Animation hide_fab_1;
    boolean isShow=false;
    FloatingActionButton FAButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
 
        fab1 =(FloatingActionButton) findViewById(R.id.fab_1);
        FAButton =(FloatingActionButton) findViewById(R.id.mainFab);
        FAButton.setOnClickListener(this);

         show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
         hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);


       

        displayFab();
        hideFab();


    }

    private void hideFab() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);
    }

    private void displayFab() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);
    
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
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.mainFab:
                if(isShow){
                    hideFab();
                    isShow=false;
                }else{
                    displayFab();
                    isShow=true;
                }
                break;
        }
    }
}
