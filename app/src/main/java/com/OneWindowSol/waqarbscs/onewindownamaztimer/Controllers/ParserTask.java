package com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Imdad on 4/26/2016.
 */
public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

    JSONObject jObject;

    // Invoked by execute() method of this object
    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {

        List<HashMap<String, String>> places = null;

        com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.PlaceJsonParser placeJsonParser = new  com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.PlaceJsonParser();

        try {

            Log.d("Json: ", "Json object " + jsonData[0]);
            jObject = new JSONObject(jsonData[0]);

            /** Getting the parsed data as a List construct */
            places = placeJsonParser.parse(jObject);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return places;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {

        if (list == null) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");


            Log.d("Places", "onPostExecute: " + name);

        }
    }
}