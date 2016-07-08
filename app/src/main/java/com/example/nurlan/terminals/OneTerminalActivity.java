package com.example.nurlan.terminals;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class OneTerminalActivity extends AppCompatActivity implements OnMapReadyCallback {


    String oneLat;
    String oneLongt;
    String oneName;
    String LOG_TAG = "nb_log";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_terminal);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        oneLat = getIntent().getStringExtra("point_lat");
        oneLongt = getIntent().getStringExtra("point_long");
        oneName = getIntent().getStringExtra("note_text");
        Log.d(LOG_TAG, "point_lat и point_long  " + oneLat + " и " + oneLongt);

        getSupportActionBar().isShowing ();
        getSupportActionBar().setTitle(oneName);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myPoint = new LatLng(Double.valueOf(oneLat), Double.valueOf(oneLongt));
        mMap.addMarker(new MarkerOptions().position(myPoint).title(oneName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPoint, 17));
    }
}
