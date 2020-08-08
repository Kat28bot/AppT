package com.example.myapplication.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MapFragment;
import com.example.myapplication.MapsActivity;
import com.example.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment
     /*   implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener*/
{

    private HomeViewModel homeViewModel;
    private MapsActivity mapsActivity;
    private HomeFragment homeFragment;
    MapFragment mapFragment;
    MainActivity mainActivity;

    private GoogleMap googleMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Activity main = mainActivity;

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        //mapsActivity=ViewModelProviders.of(this).get(MapsActivity.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // fragment_home
      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

       /* if (checkSelfPermission("ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            mMap.setMyLocationEnabled(true);
        }*/

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
      /*  mSydney=mMap.addMarker(
                new MarkerOptions().position(sydney).title("Sydney"));
        mSydney.setTag(0);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Integer clickCount = (Integer) marker.getTag();

                        // Check if a click count was set, then display the click count.
                        if (clickCount != null) {
                            clickCount = clickCount + 1;
                            marker.setTag(clickCount);

                            Toast.makeText(mapsActivity,
                                    marker.getTitle() +
                                            " has been clicked " + clickCount + " times.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                Log.i("sinf","sydney?");
            }
        });
        //onMapReady(mMap);
        Log.i("info","onCreateDone");*/

        final TextView textView = root.findViewById(R.id.text_home);
        //final View fragment = root.findViewById(R.id.fragmentus);
//        MapView mapFragment=(MapView) root.findViewById(R.id.fragmentus);
        MapView mapFragment = root.findViewById(R.id.mapView);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (canAccessLocation()) {
            //requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        //}

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        }
        homeViewModel.drawMap();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
               // fragment.setActivated(true);
            }
        });
        return root;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(this.getContext(),perm));
    }
/*
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("sinf","sydney?1");
        mMap = googleMap;

       /* if (checkSelfPermission("ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            mMap.setMyLocationEnabled(true);
        }*/

        // Add a marker in Sydney and move the camera
    /*    LatLng sydney = new LatLng(-34, 151);
      /*  mSydney=mMap.addMarker(
                new MarkerOptions().position(sydney).title("Sydney"));
        mSydney.setTag(0);*/
    /*    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerClickListener(this);
        Log.i("sinf","sydney?");
        // mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(mapsActivity,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }*/
}
