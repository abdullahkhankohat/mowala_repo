package flp.oursols.com.flp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import flp.oursols.com.flp.models.PlacesResult;


public class GetAddress extends AsyncTask<LatLng, Void, String> {
    private LatLng mLatLng;
    private Context mContext;
    private EventBus mBus = EventBus.getDefault();
    private OnGetAddress mCallBack;
    private boolean isPickUp = false;

    public GetAddress(Context context){
        mContext = context;
//        Dialogs.INSTANCE.showLoader(context);
    }

    public GetAddress(Context context, OnGetAddress callBack, boolean type){
        mContext = context;
        mCallBack = callBack;
        isPickUp = type;
//        Dialogs.INSTANCE.showLoader(context);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(LatLng... params) {
        try {
            mLatLng = params[0];
            Geocoder geocoder = new Geocoder(mContext,
                    Locale.getDefault());

            List<Address> addresses;

            addresses = geocoder.getFromLocation(params[0].latitude,
                    params[0].longitude, 1);

            String add = addresses.get(0).getAddressLine(0) + ","
                    + addresses.get(0).getSubLocality() + ";"
                    + addresses.get(0).getSubAdminArea();
            System.out.println(add);
            return add.replace(",null", "").replace(";null", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            PlacesResult placesResult = new PlacesResult(result, StringUtils.EMPTY,
                    mLatLng.latitude, mLatLng.longitude);

            if(null != mCallBack){
                mCallBack.onGetLocation(placesResult, isPickUp);
            } else {
                mBus.post(placesResult);
            }
//            Dialogs.INSTANCE.dismissDialog();
        }
    }
}
