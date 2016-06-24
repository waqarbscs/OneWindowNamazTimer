package com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Imdad on 5/4/2016.
 */
public class AppManager {

    public static Context context;

    private LatLng _CurrentLatLong;

    private static AppManager ourInstance = new AppManager();

    public static AppManager getInstance() {
        return ourInstance;
    }

    private AppManager() {
    }


    public void setCurrentLatLong(LatLng pCurrentLatLong) {
        _CurrentLatLong = pCurrentLatLong;
    }

    public LatLng getUserLatLong() {
        return _CurrentLatLong;
    }


    //this will tell you wheather the interent is connected or not ..
    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
