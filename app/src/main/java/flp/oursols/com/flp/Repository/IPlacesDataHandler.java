package flp.oursols.com.flp.repository;


import flp.oursols.com.flp.models.PlacesList;

public interface IPlacesDataHandler {
    void onPlacesResponse(PlacesList response);
   // void onFareEstimateResponse(FareEstimateResponse response);
    void onError(String error);
}
