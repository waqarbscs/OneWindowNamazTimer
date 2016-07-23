package com.OneWindowSol.waqarbscs.onewindownamaztimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.AdsTask;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Database.hadithDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ResideMenu resideMenu;
    public ResideMenuItem FindMosque;
    public ResideMenuItem UpdateNamazTime;
    public ResideMenuItem Silent;
    public ResideMenuItem AboutUs;
    private MainActivity mContext;
    public ResideMenuItem QiblaDirection;
    TextView TilteTextView;
    public static final String PREFS_NAME = "LoginPrefs";

    String  AdsImages,Status;
    int Id,TimeSpan=0;

    private Handler handler;

    public int mnb=0;

    Button btn_icon;
    Ads ads;
/*
    private Runnable mRunnableServerTask = new Runnable() {
        @Override
        public void run() {

            SharedPreferences abc=getSharedPreferences("ads",0);
            int a=Integer.parseInt(abc.getString("TimeSpan","30"));
            String asd=abc.getString("AdsImages","menu");
            if(mnb!=0) {
                if(AppManager.getInstance().isInternetAvailable()) {
                    AdsTask1 adsTask = new AdsTask1();
                    adsTask.execute("http://www.onewindowsol.com/NimazTime/getAds.php");
                }else{
                    ads.showDialog(MainActivity.this,asd);
                }
            }
            if(mnb==1||mnb==2||mnb==0) {
                if (handler != null)
                    handler.postDelayed(mRunnableServerTask, a * 1000);
            }
            else{
                    if(handler != null&&TimeSpan!=0)
                        handler.postDelayed(mRunnableServerTask,a*1000);
                }
            mnb++;
        }
    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdBuddiz.setPublisherKey("468caabc-b312-4bbf-93b1-659592a893b7");
        AdBuddiz.cacheAds(this); // this = current Activity
        mContext = this;
    /*
        handler = new Handler();
        handler.postDelayed(mRunnableServerTask,30*1000);
*/
        TilteTextView=(TextView)findViewById(R.id.TitleText);
        if( savedInstanceState == null ) {
            changeFragment(new MainFragment());
            TilteTextView.setText("Mosque Information");
        }
        getSupportActionBar().hide();
        setup();

        btn_icon=(Button)findViewById(R.id.ico);
        ads = new Ads();

    }

    public void setup(){
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(.6f);
        resideMenu.attachToActivity(this);
        resideMenu.openMenu(0);
        //resideMenu.setUse3D(true);

        // create menu items;
        FindMosque  = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_home,"Mosques");
        UpdateNamazTime  = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_profile,  "Namaz Timing");
        Silent = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_calender, "Mobile Silent");
        AboutUs = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_setting, "About Us");
        QiblaDirection = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.qibla_direction, "Qibla");

        FindMosque.setMinimumWidth(50);

        // create menu items;
        FindMosque.setOnClickListener(this);
        UpdateNamazTime.setOnClickListener(this);
        Silent.setOnClickListener(this);
        AboutUs.setOnClickListener(this);
        QiblaDirection.setOnClickListener(this);

        resideMenu.addMenuItem(FindMosque,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(UpdateNamazTime,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(Silent,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(AboutUs,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(QiblaDirection,ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
            }
        });

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
    }



    @Override
    public void onClick(View v) {

        if(v==FindMosque){
            if(!AppManager.getInstance().isInternetAvailable()){
                resideMenu.openMenu(0);
            }else {
                resideMenu.closeMenu();
            }
            changeFragment(new MainFragment());
            TilteTextView.setText("Mosque Information");

        }else
        if(v==UpdateNamazTime){
            TilteTextView.setText("Karachi"+" Namaz Timing");
            //btn_icon.setVisibility(View.VISIBLE);
            changeFragment(new UpdateNamazTime());
            UpdateNamazTime.startViewTransition(v);
            resideMenu.closeMenu();
        }else
        if(v==Silent){
            TilteTextView.setText("Prayer Time Silent");
            changeFragment(new SilentMobile());
            resideMenu.closeMenu();
            //stop adds
            /*
            if(i==3||i==5)
            AdBuddiz.showAd(this);
            */
        }else
        if(v==AboutUs){
            TilteTextView.setText("OneWindowSol");
            changeFragment(new About());
            resideMenu.closeMenu();
        }else if(v==QiblaDirection){
            TilteTextView.setText("Qibla Direction");
            Intent inta=new Intent(this,Qibla_Direction.class);
            startActivity(inta);
            //Ads ads = new Ads();

            //ads.showDialog(MainActivity.this);

        }

    }
    /*
    public class AdsTask1 extends AsyncTask<String, Integer, String> {

        String data = null;
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

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jObject = new JSONObject(s);
                JSONObject adsObject = jObject.getJSONObject("adsObject");
                AdsImages=adsObject.getString("AdsImages");
                 Id=Integer.parseInt(adsObject.getString("Id"));
                TimeSpan=Integer.parseInt(adsObject.getString("TimeSpan"));
                Status=adsObject.getString("Status");

                SharedPreferences adsPreference=getSharedPreferences("ads",0);
                SharedPreferences.Editor adsEdit=adsPreference.edit();
                adsEdit.putString("TimeSpan",Integer.toString(TimeSpan));
                adsEdit.putString("AdsImage",AdsImages);
                adsEdit.putString("Status",Status);
                adsEdit.apply();


                if(Status.equals("true")) {


                    ads.showDialog(MainActivity.this,AdsImages);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
           // Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
           // Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
//        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
    boolean doubleBackToExitPressedOnce = false;
    //Defined in Activity class, so override

    @Override
    protected void onDestroy() {
       // handler.removeCallbacks(mRunnableServerTask);
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
            if (resideMenu.isOpened()) {
                //handler.removeCallbacks(mRunnableServerTask);
                super.onBackPressed();
            }
            else{
                resideMenu.openMenu(0);
            }
    }
}