package flp.oursols.com.flp.repository;

import android.content.Context;

import flp.oursols.com.flp.models.PlacesList;
import flp.oursols.com.flp.rest.IResponseCallBack;
import flp.oursols.com.flp.rest.RestRequestHandler;


public class PlacesRepository {

    private Context mContext;
    private IPlacesDataHandler mUserCallback;
    private RestRequestHandler mRestRequestHandler;

    public PlacesRepository() {
        mRestRequestHandler = new RestRequestHandler();
    }

    public void getNearbyPlaces(IPlacesDataHandler handler,
                                String location, String nextPageToken) {
        mUserCallback = handler;
        mRestRequestHandler.getNearbyPlaces(location, nextPageToken, mNearbyPlacesCallBack);
    }

    private IResponseCallBack mNearbyPlacesCallBack = new IResponseCallBack() {
        @Override
        public void onResponse(Object object) {
            if(object instanceof PlacesList) {
                mUserCallback.onPlacesResponse((PlacesList) object);
            }
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error) {
            mUserCallback.onError(error);
        }

        @Override
        public void onSendCodeSuccess() {

        }
    };



}
