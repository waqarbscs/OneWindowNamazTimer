package com.OneWindowSol.waqarbscs.onewindownamaztimer;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.DirectionClasses.DirectionFinder;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.DirectionClasses.DirectionFinderListener;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.DirectionClasses.Route;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DirectionalMap extends AppCompatActivity implements DirectionFinderListener {

    private GoogleMap mGoogleMap;

    String longitude;
    String latitude;
    String originlongitude;
    String originlatitude;

    String placeid;
    String masjidname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_directional_map);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting Google Map
        mGoogleMap = fragment.getMap();

        placeid = getIntent().getStringExtra("PlaceId");
        Log.w("PlaceId", "" + placeid);

        masjidname = getIntent().getStringExtra("MasjidName");

        longitude = getIntent().getStringExtra("Longitude").toString();
        latitude = getIntent().getStringExtra("Latitude").toString();


        if (AppManager.getInstance().isInternetAvailable()) {

            try {
                new DirectionFinder(DirectionalMap.this, AppManager.getInstance().getUserLatLong(), new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).execute();
            } catch (UnsupportedEncodingException uex) {
                Log.d("Exception", "Ex: " + uex.getMessage());
            } catch (IllegalArgumentException iArgument) {
                Toast.makeText(this, "Can't find Location.", Toast.LENGTH_SHORT).show();
                Log.d("Exception", "Ex: " + iArgument.getMessage());
            }
        }


    }

    ProgressDialog progressDialog;

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

    }

    List<Marker> originMarkers;
    List<Marker> destinationMarkers;
    List<Polyline> polylinePaths;

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        PolylineOptions polylineOptions;
        for (Route route1 : route) {

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route1.startLocation, 18));
            //((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            //((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(route1.startAddress)
                    .position(route1.startLocation)
            ));
            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(masjidname)
                    .position(route1.endLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.a)
                    )
            ));

            polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);
            polylinePaths.clear();
            for (int i = 0; i < route1.points.size(); i++)
                polylineOptions.add(route1.points.get(i));
            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
    }


}
