package com.route.gpstrackerg2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.route.Base.BaseActivity;
import com.route.Utils.MyLocationProvider;

import java.util.Arrays;

public class MainActivity extends BaseActivity implements LocationListener
,OnMapReadyCallback {


    public static final int MY_PERMISSIONS_REQUEST_LOCATION_CODE = 500;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_CODE = 400;

    MapView mapView;
    AutocompleteSupportFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView=findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                showMessage(place.getName(),place.getAddress()+place.getLatLng().toString(),"ok");
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
        if (isLocationPermissionAllowed()) {
            // do your func
            getUserLocation();

        } else {
            requestLocationPermission();
        }
    }

    Marker currentLocationMarker;
    GoogleMap googleMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        ChangeUserMarkerLocation();
    }
    public void ChangeUserMarkerLocation(){
        if(currentLocation!=null&&googleMap!=null){
            LatLng latLng= new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
            MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("you are here");
            if(currentLocationMarker==null)
            currentLocationMarker=googleMap.addMarker(markerOptions);
            else
                currentLocationMarker.setPosition(latLng);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.0f));

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void requestLocationPermission() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            showConfirmationMessage(R.string.warning, R.string.location_explanation,
                    R.string.ok
                    , new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);
                        }
                    });

        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);
        }
    }

    boolean isLocationPermissionAllowed() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //call your function
                    getUserLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(activity, "we cannot get your nearest friends . permission is denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    MyLocationProvider locationProvider;
    Location currentLocation;
    public void getUserLocation() {
        locationProvider = new MyLocationProvider(activity, this);
        currentLocation=locationProvider.getCurrentLocation();
        if(currentLocation!=null){
            Toast.makeText(activity, currentLocation.getLatitude()+" "+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation=location;
        ChangeUserMarkerLocation();
        Toast.makeText(activity, currentLocation.getLatitude()+" "+
                currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e("provider enabled",provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e("provider disabled",provider);
    }

    public void openPlacePicker(View view) {
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
    }
}