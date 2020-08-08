package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener
        {

    private GoogleMap mMap;
    int gpsOn;
    AlertDialog GPSdialog;
    Marker mSydney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       /* Log.e("info","onCreateDone1");
        //checkGPS();*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //onMapReady(mMap);
        Log.i("info","onCreateDone");//*/
    }

            public void checkGPS() {
                try {
                    gpsOn = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                } catch (Exception e) {
                    Log.i("GPS Failure", "Sum Ting Wong");
                }
                if (gpsOn == 0) {
                    forceGPSOn();
                }
            }

            public void forceGPSOn() {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GPS jest wyłączony!");
                builder.setMessage("Do użytkowania aplikacji niezbędna jest włączona lokalizacja, czy chcesz ją włączyć?");
                builder.setPositiveButton("Włącz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 12);

                    }
                }).setNegativeButton("Nie, Dziękuję", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        checkGPS();
                        dialog.dismiss();
                    }
                });
                GPSdialog = builder.create();
                GPSdialog.show();
                if (GPSdialog.isShowing()) {
                    System.out.println("Mudafaka working");
                }

            }


            public void drawMaps() {
        checkGPS();
                //mMap.setOnMarkerClickListener(this);
                //for (int i = 0; i < zagadkiLista.size(); i++) {
                    // Log.w("lista_punktow",zagadkiLista.get(i).toString());
                    LatLng point = new LatLng(-34, 151);
                    MarkerOptions markerOptions = new MarkerOptions().position(point).title("sydney");

                    Marker marker = mMap.addMarker(markerOptions);
                    marker.setTag(1);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(point);
                    circleOptions.radius(50);
                    circleOptions.strokeColor(Color.BLACK);
                    circleOptions.fillColor(0x0ff000);
                    circleOptions.strokeWidth(1);
                    mMap.addCircle(circleOptions);
               // }
            }


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
    @Override
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
        LatLng sydney = new LatLng(-34, 151);
      /*  mSydney=mMap.addMarker(
                new MarkerOptions().position(sydney).title("Sydney"));
        mSydney.setTag(0);*/
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
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
                    Toast.makeText(this,
                            marker.getTitle() +
                                    " has been clicked " + clickCount + " times.",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }
