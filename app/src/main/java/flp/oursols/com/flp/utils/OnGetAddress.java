package flp.oursols.com.flp.utils;


import flp.oursols.com.flp.models.PlacesResult;

public interface OnGetAddress {
    public void onGetLocation(PlacesResult placesResult, boolean type);
}