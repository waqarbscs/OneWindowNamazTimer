package com.example.waqarbscs.onewindownamaztimer.Controllers;

import android.os.AsyncTask;
import android.util.Log;


import com.example.waqarbscs.onewindownamaztimer.Interfaces.OnPlacesLoadCompletion;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Imdad on 4/26/2016.
 */
public class PlacesTask extends AsyncTask<String, Integer, String> {

    String data = null;
    public OnPlacesLoadCompletion delegate = null;

    // Invoked by execute() method of this object
    @Override
    protected String doInBackground(String... url) {

        try {

            data = downloadUrl(url[0]);
            Log.d("Data", data);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }

        return data;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(String result) {
        Log.d("Data Load: ", result);
        ParserTask parserTask = new ParserTask();
        parserTask.execute(result);

        List<HashMap<String, String>> places = null;

        com.example.waqarbscs.onewindownamaztimer.Controllers.PlaceJsonParser placeJsonParser = new  com.example.waqarbscs.onewindownamaztimer.Controllers.PlaceJsonParser();

        try {

            Log.d("Json: ", "Json object " + result);
            JSONObject jObject = new JSONObject(result);

            /** Getting the parsed data as a List construct */
            places = placeJsonParser.parse(jObject);

            if (delegate != null)
                delegate.OnProcessFinish(places);


        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }

    }


    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
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