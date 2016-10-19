package flp.oursols.com.flp.rest;

import flp.oursols.com.flp.models.PlacesList;
import flp.oursols.com.flp.prefrences.PreferencesKeys;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface IRestClient {


    @GET(PreferencesKeys.PLACES_SEARCH_EXT_URL)
    Call<PlacesList> getNearbyPlaces(@Query(Fields.GooglePlaces.LOCATION) String location,
                                     @Query(Fields.GooglePlaces.RADIUS) String radius,
                                     @Query(Fields.GooglePlaces.KEY) String key,
                                     @Query(Fields.GooglePlaces.NEXT_PAGE_TOKEN) String next);


}
