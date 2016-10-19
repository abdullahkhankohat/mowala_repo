package flp.oursols.com.flp.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import flp.oursols.com.flp.R;
import flp.oursols.com.flp.utils.ActivityStackManager;
import flp.oursols.com.flp.utils.Utils;

public class SplashScreen extends AppCompatActivity {


    private SplashScreen mCurrentActivity;
    private ActivityStackManager mStackManager;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mCurrentActivity=this;
        mStackManager = ActivityStackManager.getInstance(mCurrentActivity);
        mStackManager.startLocationService();
        SplashTime();
    }


    public void SplashTime() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                mStackManager.startMainHomeScreen();

            }
        }, SPLASH_TIME_OUT);
    }


}
