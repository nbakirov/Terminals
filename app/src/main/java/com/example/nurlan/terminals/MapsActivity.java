package com.example.nurlan.terminals;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat = 0.0;//широта
    double longT = 0.0;//долгота


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().isShowing();
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
        TerminalsDatabase db = new TerminalsDatabase(MapsActivity.this);
        List<Point> tlist = db.getAllPoints();
        for (int i = 0; i < tlist.size(); i++) {
            LatLng ll = new LatLng(tlist.get(i).getPoint_lat(), tlist.get(i).getPoint_longt());
            mMap.addMarker(new MarkerOptions().position(ll).title(String.valueOf(tlist.get(i).getPoint_name())));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tlist.get(0).getPoint_lat(), tlist.get(0).getPoint_longt()), 15));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.developer_info) {
            Intent i = new Intent(MapsActivity.this, DeveloperInfo.class);
            startActivity(i);
        }

        if (id == R.id.terminals_list) {
            Intent i = new Intent(MapsActivity.this, TerminalsListActivity.class);
            startActivity(i);
        }
        if (id == R.id.myposition) {

          GPSTracker gp = new GPSTracker(MapsActivity.this);


            if (gp.canGetLocation()) {
                lat = gp.getLatitude();
                longT = gp.getLongitude();

            } else {
                gp.showSettingsAlert();
            }
            LatLng mp = new LatLng((lat),(longT));


               mMap.addMarker(new MarkerOptions()
                       .icon(BitmapDescriptorFactory.fromResource(R.drawable.mymap_icon))
                       .title("Вы находитесь здесь")
                       .position(mp));
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mp, 15));

        }
        return super.onOptionsItemSelected(item);
    }
}
