package com.example.princ.inclass09;

/*Assignment# - InClass09
  Names : Sujanth Babu Guntupalli
          Mounika Yendluri
*/

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PathsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    InputStream is;
    InputStreamReader reader;
    ArrayList<Trip.Point> points;
    ArrayList<LatLng> llPoints;
    Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paths);
        setTitle("Paths Activity");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            is = getResources().openRawResource(R.raw.trip);
            reader = new InputStreamReader(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Trip trip = gson.fromJson(reader, Trip.class);
        points = trip.points;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        llPoints = new ArrayList<>();
        for(Trip.Point point:points) {
            LatLng newPoint = new LatLng(point.latitude, point.longitude);
            llPoints.add(newPoint);
        }
        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true).addAll(llPoints);
        line=mMap.addPolyline(options);
        try {
            mMap.addMarker(new MarkerOptions().position(line.getPoints().get(0)).title("start point"));
            mMap.addMarker(new MarkerOptions().position(line.getPoints().get(line.getPoints().size()-1)).title("end point"));
        }catch (Exception e){
            e.printStackTrace();
        }
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : line.getPoints()) {
                    builder.include(latLng);
                }
                LatLngBounds bounds=builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,100));
            }
        });
    }
}
