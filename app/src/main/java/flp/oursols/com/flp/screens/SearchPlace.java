package flp.oursols.com.flp.screens;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

import flp.oursols.com.flp.R;
import flp.oursols.com.flp.models.Place;
import flp.oursols.com.flp.models.PlacesList;
import flp.oursols.com.flp.models.PlacesResult;
import flp.oursols.com.flp.prefrences.AppPreferences;
import flp.oursols.com.flp.repository.IPlacesDataHandler;
import flp.oursols.com.flp.repository.PlacesDataHandler;
import flp.oursols.com.flp.repository.PlacesRepository;
import flp.oursols.com.flp.screens.adapters.PlacesListViewAdapter;
import flp.oursols.com.flp.utils.Constants;

public class SearchPlace extends AppCompatActivity {

    private String mPlacesTag;
    private SearchPlace mCurrentActivity;
    private GoogleApiClient mGoogleApiClient;
    private PlacesListViewAdapter mRecentPlacesAdapter;
    private PlacesListViewAdapter mNearByPlacesAdapter;
    private ArrayList<Place> mRecentPlaces;
    private ArrayList<Place> mNearByPlaces;
    private PlacesRepository mPlacesRepository;
    private boolean mShowFareEstimate;
    private String mTitle;

    ListView mListViewPlaces;
    ListView mNearByPlacesListView;
    private String mNextPageToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_main);
        mCurrentActivity = this;
        mListViewPlaces =(ListView) findViewById(R.id.recentPlacesList);
        mNearByPlacesListView =(ListView) findViewById(R.id.nearByPlacesList);
        mPlacesTag = getIntent().getStringExtra(Constants.PLACES_TAG);
        mTitle = getIntent().getStringExtra(Constants.PLACES_TITLE);
        mShowFareEstimate = getIntent().getBooleanExtra(Constants.PLACES_TYPE, false);
        init();
    }

    /*
    *  gets Google Place Api client
    * */

    private void getGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(mCurrentActivity)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }


    /*
    *  Sets Google Places Auto Complete Widget
    * */
    private void setAutoCompleteWidget() {
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        if (autocompleteFragment != null) {
            autocompleteFragment.setHint(getResources().getString(R.string.search_hint));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(com.google.android.gms.location.places.Place place) {
                    Place selectedPlace = new Place();
                    selectedPlace.geometry = new Place.Geometry();
                    selectedPlace.geometry.location = new Place.Location();
                    selectedPlace.name = place.getName().toString();
                    selectedPlace.id = place.getId();
                    selectedPlace.vicinity = place.getAddress().toString();
                    LatLng latLng = place.getLatLng();
                    selectedPlace.geometry.location.lat = latLng.latitude;
                    selectedPlace.geometry.location.lng = latLng.longitude;
                    AppPreferences.setRecentPlaces(mCurrentActivity, selectedPlace);
                    //set intent to get results in onActivityResults of calling activity or fragment
                    PlacesResult placesResult = new PlacesResult(selectedPlace.name, selectedPlace.vicinity,selectedPlace.geometry.location.lat,
                            selectedPlace.geometry.location.lng);
                    Intent returnIntent = new Intent();
                    //PlacesResult data model implements Parcelable so we could pass object in extras
                    returnIntent.putExtra(Constants.PLACES_RESULT, placesResult);
                    setResult(Activity.RESULT_OK, returnIntent);
                    mCurrentActivity.finish();
                }

                @Override
                public void onError(Status status) {
                }
            });
        }
    }


    private void init(){
        mRecentPlaces = new ArrayList<Place>();
        mNearByPlaces = new ArrayList<Place>();
        mNearByPlacesAdapter = new PlacesListViewAdapter(mCurrentActivity, mNearByPlaces);
        mNearByPlacesListView.setAdapter(mNearByPlacesAdapter);
        mPlacesRepository = new PlacesRepository();


        getGoogleApiClient();
        setAutoCompleteWidget();
        fetchRecentPlaces();
        fetchNearByPlaces();
        setNearByPlacesAdapter();
    }
    public void fetchRecentPlaces(){
        new FetchRecentPlacesTask().execute();
    }
    /*
    * Connects Google Api Client
    * */
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    /*
    * Disconnects Google Api Client
    * */
    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    /*
    * Sets on click listener on Home button in action bar
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                mCurrentActivity.finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private synchronized void fetchNearByPlaces() {
        Log.e("mNextPageToken", "token = " + mNextPageToken);
       // Dialogs.INSTANCE.showLoader(mCurrentActivity);
        //TODO: add location here
        mPlacesRepository.getNearbyPlaces(mPlacesDataHandler, AppPreferences.getLatitude(SearchPlace.this)+","+AppPreferences.getLongitude(SearchPlace.this), mNextPageToken);
        mNextPageToken = StringUtils.EMPTY;
    }

    private class FetchRecentPlacesTask extends AsyncTask<Void, Void, ArrayList<Place>> {

        @Override
        protected ArrayList<Place> doInBackground(Void... params) {
            return AppPreferences.getRecentPlaces(mCurrentActivity);
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places) {
            super.onPostExecute(places);
            if (places != null && places.size() > 0) {
                mRecentPlaces.clear();
                mRecentPlaces = new ArrayList<>();
                mRecentPlaces.addAll(places);
                setAdapter();
            }
        }
    }

    private void setAdapter() {
        mRecentPlacesAdapter = new PlacesListViewAdapter(mCurrentActivity, mRecentPlaces);
        mListViewPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place selectedPlace = mRecentPlacesAdapter.getItem(position);
                AppPreferences.setRecentPlaces(mCurrentActivity, selectedPlace);
                //set intent to get results in onActivityResults of calling activity or fragment
                PlacesResult placesResult = new PlacesResult(selectedPlace.name, selectedPlace.vicinity,selectedPlace.geometry.location.lat,
                        selectedPlace.geometry.location.lng);
                Intent returnIntent = new Intent();
                //PlacesResult data model implements Parcelable so we could pass object in extras
                returnIntent.putExtra(Constants.PLACES_RESULT, placesResult);
                mCurrentActivity.setResult(Activity.RESULT_OK, returnIntent);

            }
        });
        mListViewPlaces.setAdapter(mRecentPlacesAdapter);
    }

    private void setNearByPlacesAdapter(){
        mNearByPlacesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place selectedPlace = mNearByPlacesAdapter.getItem(position);
                AppPreferences.setRecentPlaces(mCurrentActivity, selectedPlace);
                //set intent to get results in onActivityResults of calling activity or fragment
                PlacesResult placesResult = new PlacesResult(selectedPlace.name, selectedPlace.vicinity,selectedPlace.geometry.location.lat,
                        selectedPlace.geometry.location.lng);
                Intent returnIntent = new Intent();
                //PlacesResult data model implements Parcelable so we could pass object in extras
                returnIntent.putExtra(Constants.PLACES_RESULT, placesResult);
                mCurrentActivity.setResult(Activity.RESULT_OK, returnIntent);
                mCurrentActivity.finish();
            }
        });
    }

    private IPlacesDataHandler mPlacesDataHandler = new PlacesDataHandler() {


        @Override
        public void onPlacesResponse(final PlacesList response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // Dialogs.INSTANCE.dismissDialog();

                    if (response.next_page_token != null) {
                        mNextPageToken = response.next_page_token;
                    } else {
                        mNextPageToken = StringUtils.EMPTY;
                    }

                    mNearByPlaces.addAll(response.results);
                    mNearByPlacesAdapter.addAll(response.results);
                    mNearByPlacesAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onError(String error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // Dialogs.INSTANCE.dismissDialog();
                }
            });
            //Toast.makeText(getActivity(), error + "", Toast.LENGTH_LONG).show();
        }
    };
}
