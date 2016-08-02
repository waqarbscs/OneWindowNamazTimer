package com.OneWindowSol.waqarbscs.onewindownamaztimer;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Adapters.CustomMasjidListAdapter;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Adapters.RecyclerViewAdapter;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.ParserTask;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Interfaces.OnPlacesLoadCompletion;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers.NetworkUtil;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment
        implements LocationListener, View.OnClickListener, OnPlacesLoadCompletion, OnMapReadyCallback {

    private int PLACE_PICKER_REQUEST = 1;
    public Location location = null;
    private static final String TAG = MainFragment.class.getSimpleName();
    GoogleMap mGoogleMap;
    Spinner mSprPlaceType;

    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;

    double mLatitude = 0;
    double mLongitude = 0;

    PlacePicker.IntentBuilder builder;
    Activity activity;

    List<Masjids> _masjids;
    private CustomMasjidListAdapter adapter;
    public RecyclerViewAdapter rcAdapter;
    private ListView _listView;

    private RecyclerView rcView;

    private ProgressDialog pDialog;


    private BroadcastReceiver mBroadCastReceiver;
    private IntentFilter mNetworkStateChangedFilter;

    static Activity instance;

    public static Activity getInstance() {
        return instance;
    }
    LocationManager locationManager;

    public static final String PREFS_NAME = "LoginPrefs";
    View ParentView;

    //Button buttonSilent;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ParentView = inflater.inflate(R.layout.fragment_main, container, false);
        AppManager.context = getActivity();
        activity = getActivity();
        pDialog = new ProgressDialog(getActivity());
        _masjids = new ArrayList<>();

        rcView = (RecyclerView) ParentView.findViewById(R.id.recycleView);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rcAdapter = new RecyclerViewAdapter(_masjids);
        rcView.setAdapter(rcAdapter);



        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int status = googleAPI.isGooglePlayServicesAvailable(getActivity());

        //int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();
        } else {
            // Getting reference to the SupportMapFragment
            SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

            // Getting Google Map
            mGoogleMap = fragment.getMap();

            //mGoogleMap=fragment.getMapAsync(this);

            try {
                //mGoogleMap.setMyLocationEnabled(true);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //User has previously accepted this permission
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    //Not in api-23, no need to prompt
                    mGoogleMap.setMyLocationEnabled(true);
                }
            } catch (SecurityException ex) {
                Toast.makeText(getActivity(), "Could not get permission to get user location.", Toast.LENGTH_SHORT).show();
                Log.d("gfd", "adsfsd");
            }

            LatLng coordinate = new LatLng(25.0, 55.0);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
            mGoogleMap.animateCamera(yourLocation);

                if (IsReadyToFindMasjids()) {
                    FindTheMasjidsNearby();
                }

            }
        mNetworkStateChangedFilter=new IntentFilter();
        mNetworkStateChangedFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mBroadCastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status= NetworkUtil.getConnectivityStatusString(context);
                if(status=="Wifi enabled"||status=="Mobile data enabled"){
                    if(IsReadyToFindMasjids()){
                        FindTheMasjidsNearby();
                    }
                }
            }
        };
        return ParentView;
    }
    @Override
    public void onResume() {
        super.onResume();
         getActivity().registerReceiver(mBroadCastReceiver,mNetworkStateChangedFilter);

        getActivity().unregisterReceiver(mBroadCastReceiver);
        getActivity().registerReceiver(mBroadCastReceiver, mNetworkStateChangedFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mBroadCastReceiver);
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //  TODO: Prompt with explanation!

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }
    public void LocationSettings() {


        // Getting LocationManager object from System Service LOCATION_SERVICE

        if (locationManager == null)
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location From GPS

        try {
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

           // SharedPreferences locPreference=getActivity().getSharedPreferences("loc",0);
           // SharedPreferences.Editor editloctaion=locPreference.edit();
            //editloctaion.putString("localat",Double.toString(location.getLatitude()));
           // editloctaion.putString("loclong",Double.toString(location.getLongitude()));
           // editloctaion.apply();

            if (location != null) {
                AppManager.getInstance().setCurrentLatLong(new LatLng(location.getLatitude(), location.getLongitude()));
                //new geoLocation().execute();
                //getGeoCoder();
            }

        } catch (SecurityException ex) {
            Toast.makeText(getActivity(), "Could not get permission to get user last known location.", Toast.LENGTH_SHORT).show();
        }

        if (location != null) {
            onLocationChanged(location);
        }
        float tempVal = 0.0f;
        long lValue = 10000;

        try {
            //error in sdk 6.0
            //locationManager.requestLocationUpdates(provider, lValue, tempVal, this);
            //mGoogleMap.setMyLocationEnabled(true);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //User has previously accepted this permission
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(provider, lValue, tempVal, this);
                }
            } else {
                //Not in api-23, no need to prompt
                locationManager.requestLocationUpdates(provider, lValue, tempVal, this);
            }
        } catch (SecurityException ex) {
            Toast.makeText(getActivity(), "location update security issue.", Toast.LENGTH_SHORT).show();
        }

        if (location == null) {
            location = mGoogleMap.getMyLocation();
            onLocationChanged(location);
        }
    }

    private void getGeoCoder() {
        Geocoder geocoder= new Geocoder(getContext(), Locale.ENGLISH);

        try {

            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 5);

            if(addresses != null) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }

                //myAddress.setText("I am at: " +strAddress.toString());

            }
            
               // myAddress.setText("No location found..!");

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getActivity(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onResume() {

        super.onResume();

    }
*/

    @Override
    public void onLocationChanged(Location location) {

        if (location == null)
            return;

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        AppManager.getInstance().setCurrentLatLong(latLng);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

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

    public boolean IsReadyToFindMasjids() {

        LocationSettings();
        LocationManager lm = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(getContext(), "Please Enable GPS for Current location Nearby Mosques.", Toast.LENGTH_SHORT).show();
        }
        if(location==null){

        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

    }


        if (!AppManager.getInstance().isInternetAvailable()) {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //builder.setTitle("Interent Service Not Enabled");
            builder.setMessage("Please enable Internet Services");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);


                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

                Toast.makeText(getActivity(), "Please connect to internet.", Toast.LENGTH_SHORT).show();



            return false;
        }

        if (location == null) {

            Toast.makeText(getActivity(), "could not found any location.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void FindTheMasjidsNearby() {
//        int selectedPosition = mSprPlaceType.getSelectedItemPosition();
//        String radius = mPlaceType[selectedPosition];
        String radius="1000";

        Log.d("Radius", radius);

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + location.getLatitude() + "," + location.getLongitude());
        sb.append("&radius=" + radius);
        sb.append("&types=" + "mosque");
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyD0l49MCDJiMlvjjytVTZFJZT4OawW-Tfg");

        // Creating a new non-ui thread task to download json data
        com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.PlacesTask placesTask = new com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.PlacesTask();

        placesTask.delegate = this;

        placesTask.execute(sb.toString());
        pDialog.setTitle("Loading");
        pDialog.setMessage("Loading Masjids Nearby...");
        pDialog.show();
    }

    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {

            case R.id.btn_find:

                if (!IsReadyToFindMasjids()) {
                    break;
                }

                FindTheMasjidsNearby();

                break;
        }
        */
    }


    //custom events of course..
    @Override
    public void OnProcessFinish(List<HashMap<String, String>> places) {

        mGoogleMap.clear();//first clear the map
        _masjids.clear();
        pDialog.dismiss();

        if (places == null || places.size() <= 0) {
            Toast.makeText(getActivity(), "No masjid found.", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < places.size(); i++) {

            Masjids currentMasjid = new Masjids();
            currentMasjid.setInitialValues();

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Getting a place from the places list
            HashMap<String, String> hmPlace = places.get(i);

            Log.d("hmobject", "" + hmPlace);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");
            String placeid = hmPlace.get("place_id");
            Log.d("Places", " placeid: " + placeid);

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");

            currentMasjid.setMasjidName(name);
            currentMasjid.setPlaceId(placeid);
            currentMasjid.setVacinity(vicinity);
            currentMasjid.setLatLong(new LatLng(lat, lng));

            //mGoogleMap.addMarker(new MarkerOptions()
            //        .position(new LatLng(lat, lng))
            //        .title(name));


            Log.d("Places", "OnProcess: " + name + " Vicinity: " + vicinity + " placeid: " + placeid);

            _masjids.add(currentMasjid);

        }

        Log.d("MasjidCount: ", "" + _masjids.size());
        rcAdapter.notifyDataSetChanged();

    }
    class geoLocation extends AsyncTask<String,Void,String>{
        String data = null;
        @Override
        protected String doInBackground(String... params) {

            try {
                data=downloadData("https://maps.googleapis.com/maps/api/geocode/json?latlng="+location.getLatitude()+","+location.getLongitude()+"&key=AIzaSyDZLqRFRSUIncfiS8KXaHHw52YgdZyI6OQ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject js=new JSONObject(result);
                JSONObject jsonArray=js.getJSONObject("results");




            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

        }
        private String downloadData(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);
                Log.d("data", "Downloading from: " + url);
                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();
                Log.d("data", "Downloaded: " + data);
                br.close();

            } catch (Exception e) {
                Log.d("Downloading Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }

            return data;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (!IsReadyToFindMasjids()) {
            return;
        }

        FindTheMasjidsNearby();
    }

}
