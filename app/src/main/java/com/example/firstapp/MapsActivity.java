package com.example.firstapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, LocationListener {

    private GoogleMap mMap;
    private TextView longView, latView;
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        longView = (TextView) findViewById(R.id.map_long);
        latView = (TextView) findViewById(R.id.map_lat);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        System.out.println("--------- locationManager");
        System.out.println(locationManager);
        // LocationListener locationListener = new MyLocationListener(this, longView, latView);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-122.084, 37.421 -122.084),
                        new LatLng(-121.036, 36.321 -121.036),
                        new LatLng( -120.446, 35.041 -120.446),
                        new LatLng(-119.336, 34.341 -119.336)
                ));

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 4));
        // mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
        // Set listeners for click events.
        mMap.setOnPolylineClickListener(this);


        /*
        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLatitude();
        System.out.println("---------MARKER");
        System.out.println(latitude);
        System.out.println("---------latitude");
        System.out.println(latitude);
        System.out.println("---------longitude");
        System.out.println(longitude);

        LatLng location = new LatLng(39, -9);
        mMap.addMarker(new MarkerOptions().position(location).title("My Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
        */
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    /** Called when the user taps Start/Stop Drawing button */
    public void changeBtn(View view) {
        Button mapBtn = findViewById(R.id.map_drawing_btn);
        if (mapBtn != null) {
            String btnText = (String)mapBtn.getText();
            if (btnText.equals("Start Drawing")) {
                mapBtn.setText("Stop Drawing");
            } else {
                mapBtn.setText("Start Drawing");
            }
        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
            System.out.println("---------loc");
            System.out.println(loc);
            currentLocation = loc;
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLatitude();

            LatLng location = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location).title("My Location"));

            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 4));

            if (longView != null) {
                longView.setText("Long: " + currentLocation.getLongitude());
            }

            if (latView != null) {
                latView.setText("Lat: " + currentLocation.getLatitude());
            }
        }
    }
    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}