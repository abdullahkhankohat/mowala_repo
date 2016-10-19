package flp.oursols.com.flp.rest;

import android.content.Context;

import flp.oursols.com.flp.models.PlacesList;
import flp.oursols.com.flp.utils.Constants;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class RestRequestHandler {

    private Context mContext;
    private IRestClient mRestClient;
    private IResponseCallBack mResponseCallBack;


    public void getNearbyPlaces(String location, String nextPageToken, final IResponseCallBack mDataCallback) {
        IRestClient restClient = RestClient.getGooglePlaceApiClient();
        Call<PlacesList> call = restClient.getNearbyPlaces(location, Constants.RADIUS_NEARBY_LOCATION, Constants.GOOGLE_PLACE_SERVER_API_KEY, nextPageToken);
        call.enqueue(new Callback<PlacesList>() {
            @Override
            public void onResponse(Response<PlacesList> response, Retrofit retrofit) {
                if (response.body().status.equalsIgnoreCase(Constants.STATUS_CODE_OK)) {
                    mDataCallback.onResponse(response.body());
                } else {
                    mDataCallback.onError("Could not get the locations");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mDataCallback.onError("" + t);
            }
        });
    }

}