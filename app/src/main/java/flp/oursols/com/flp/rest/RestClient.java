package flp.oursols.com.flp.rest;


import flp.oursols.com.flp.prefrences.PreferencesKeys;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class RestClient {

    private static IRestClient retrofitCalls;
    private static IRestClient retrofitGoogleApiCalls;


    public static IRestClient getGooglePlaceApiClient() {
        if (retrofitGoogleApiCalls == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(PreferencesKeys.GOOGLE_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitGoogleApiCalls = client.create(IRestClient.class);
        }
        return retrofitGoogleApiCalls;
    }
}
