package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Fragment> frgm;
    GoogleMap mMap;
    private Object SupportMapFragment;
    //private Object OnMapReadyCallback;

    @SuppressLint("ResourceType")
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        frgm = new MutableLiveData<>();
       frgm.setValue(new Fragment(R.id.map));


        LatLng point = new LatLng(-34, 151);
        MarkerOptions markerOptions = new MarkerOptions().position(point).title("sydney");

        //Marker marker = mMap.addMarker(markerOptions);
        //frgm = new ;

        //marker.setTag(1);
       // frgm.setValue();
        Log.i("hv","homeviewmodel ");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Fragment> drawMap(){
       // frgm=new GoogleMap(mMap);
        return frgm;}
    public void dMap(){
      /*  SupportMapFragment mf= com.google.android.gms.maps.SupportMapFragment.newInstance().getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });*/

    }
}