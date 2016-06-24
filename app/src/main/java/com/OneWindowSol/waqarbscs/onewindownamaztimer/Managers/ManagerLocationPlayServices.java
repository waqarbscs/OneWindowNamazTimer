package com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Imdad on 4/21/2016.
 */
public class ManagerLocationPlayServices {

    private static ManagerLocationPlayServices mInstance = null;
    private String mString;
    private Activity _activity;

    //making it private so no instance can be declared externaly
    private ManagerLocationPlayServices() {
        //the constructor
    }

    public static synchronized ManagerLocationPlayServices getInstance() {
        if (mInstance == null) {
            mInstance = new ManagerLocationPlayServices();
        }

        return mInstance;
    }

    public void SetContext(Activity pActivity) {
        //this sets current context of the application/ or say the activity that will call it
        _activity = pActivity;
    }

    public boolean CheckGooglePlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(_activity);
        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        }

        Toast.makeText(_activity.getApplicationContext(), "Could Not Connect to play services.", Toast.LENGTH_LONG).show();
        return false;
    }
}
