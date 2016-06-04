package com.example.waqarbscs.onewindownamaztimer.CustomServices;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Imdad on 5/4/2016.
 */
public class RestApiAccessService extends IntentService {

    public static final String TAG = "RestApiService";
    public static final String BroadCastAction = "com.umasstech.services.restapi";
    public static final String BroadCastActionSuccess = "com.umasstech.services.restapi.success";
    public static final String BroadCastActionError = "com.umasstech.services.restapi.error";

    private String Tag = "RestApiSer";


    public RestApiAccessService() {
        super("RestApiAccessService");

        Log.d(Tag, "Constructor");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(Tag, "OnHandleIntent");

        String action = intent.getStringExtra("Action");
        Log.d(Tag, "Action: " + action);


        if (action.equals("add") || action.equals("update")) {
            Masjids masjid = intent.getParcelableExtra("Masjid");
            SaveMasjidToServer(masjid, action);
        } else if (action.equals("getAllMasjids")) {
            String placeid = intent.getStringExtra("PlaceId");
            GetMasjidDetail(placeid);
        } else {
            throw new IllegalArgumentException("Add/Update are only allowed action");
        }


    } //keytool -list -v -keystore C:\Users\waqarbscs\.android\debug.keystore

    public void GetMasjidDetail(String placeid) {

        Map paramPost = new HashMap();
        paramPost.put("placeid", placeid);
        paramPost.put("getMasjid", "getMasjid");

        try {

            String result = getStringResultFromService_POST("http://onewindowsol.com/NimazTime/getMasjidDetail.php", paramPost);
            Log.w("Response", result);
            Log.v("testId",placeid);


            //here we get the json string: result

            JSONObject jsonObject = new JSONObject(result);
            //if we are successfull
            //than we must have object called masjidObject
            Log.d("Json", jsonObject.get("masjidObject").toString());

            JSONObject objectValues = new JSONObject(jsonObject.get("masjidObject").toString());
            Masjids masjid = new Masjids();
            masjid.setPlaceId(objectValues.getString("placeid"));
            masjid.setMasjidName(objectValues.getString("masjidname"));
            masjid.setVacinity(objectValues.getString("vacinity"));
            masjid.setAsarTime(objectValues.getString("asartime"));
            masjid.setLatLong(new LatLng(Double.parseDouble(objectValues.getString("latitude")), Double.parseDouble(objectValues.getString("longitude"))));
            masjid.setDoharTime(objectValues.getString("dohartime"));
            masjid.setFajarTime(objectValues.getString("fajartime"));
            masjid.setMagribTime(objectValues.getString("magribtime"));
            masjid.setEshaTime(objectValues.getString("eshatime"));
            masjid.setJummaTime(objectValues.getString("jummatime"));

            Intent dataRecieve = new Intent(BroadCastActionSuccess);
            dataRecieve.putExtra("Masjid", masjid);

            LocalBroadcastManager.getInstance(this).sendBroadcast(dataRecieve);


        } catch (Exception ex) {

            ex.printStackTrace();

            Intent dataRecieve = new Intent(BroadCastActionError);

            LocalBroadcastManager.getInstance(this).sendBroadcast(dataRecieve);

        }
    }


    public void SaveMasjidToServer(Masjids masjids, String action) {

        Log.w("Start", "Starting to save");
        Map paramPost = new HashMap();
        paramPost.put("MasjidName", masjids.getMasjidName());
        paramPost.put("AsarTime", masjids.getAsarTime());
        paramPost.put("DoharTime", masjids.getDoharTime());
        paramPost.put("FajarTime", masjids.getFajarTime());
        paramPost.put("MagribTime", masjids.getMagribTime());
        paramPost.put("EshaTime", masjids.getEshaTime());
        paramPost.put("Longitude", masjids.getLatLong().longitude);
        paramPost.put("Latitude", masjids.getLatLong().latitude);
        paramPost.put("PlaceId", masjids.getPlaceId());
        paramPost.put("Vacinity", masjids.getVacinity());
        paramPost.put("JummaTime", masjids.getJummaTime());

        if (action.equals("add")) {
            paramPost.put("add", "add");
        } else if (action.equals("update")) {
            paramPost.put("update", "update");
        } else {
            throw new IllegalArgumentException("Add/Update are only allowed action");
        }

        try {

            String result = getStringResultFromService_POST("http://onewindowsol.com/NimazTime/insertmasjid.php", paramPost);
            Log.w("Response", result);
            JSONObject jsonObject = new JSONObject(result);
            String res=jsonObject.getString("response");
            Log.d("ure",res);
            if(res=="updated successfully")
            Toast.makeText(RestApiAccessService.this, "Added successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getStringResultFromService_POST(String serviceURL, Map<String, String> params) {

        String resultString = null;
        HttpURLConnection httpURLConnection = null;
        String line = null;
        URL url = null;


        Log.w("URL", serviceURL);

        try {
            url = new URL(serviceURL);
        } catch (MalformedURLException urlException) {
            throw new IllegalArgumentException("URL: " + serviceURL);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append("=").append(URLEncoder.encode(String.valueOf(param.getValue())));

            if (iterator.hasNext()) {
                bodyBuilder.append("&");
            }
        }

        String body = bodyBuilder.toString();
        Log.w("BodyString", "BodyString: " + body);
        byte[] bytes = body.getBytes();

        try {
            Log.w("BodyString", "url connection opening");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            httpURLConnection.setFixedLengthStreamingMode(bytes.length);

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            //handling the response
            int requestCode = httpURLConnection.getResponseCode();
            if (requestCode != 200) {
                throw new IOException("PostFailed: StatusCode=" + requestCode);
            }

            Log.w(Tag, "Request Code: " + requestCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder buffBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                buffBuilder.append(line + "\n");
            }

            Log.w(Tag, "BuffBuilder: " + buffBuilder.toString());

            return buffBuilder.toString();
        } catch (Exception ex) {
            Log.w("BodyString", "Excption: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }

}
