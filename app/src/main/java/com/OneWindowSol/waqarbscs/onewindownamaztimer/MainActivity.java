package com.OneWindowSol.waqarbscs.onewindownamaztimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ResideMenu resideMenu;
    public ResideMenuItem FindMosque;
    public ResideMenuItem UpdateNamazTime;
    public ResideMenuItem Silent;
    public ResideMenuItem AboutUs;
    private MainActivity mContext;
    TextView TilteTextView;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdBuddiz.setPublisherKey("468caabc-b312-4bbf-93b1-659592a893b7");
        AdBuddiz.cacheAds(this); // this = current Activity
        mContext = this;
        TilteTextView=(TextView)findViewById(R.id.TitleText);
        if( savedInstanceState == null ) {
            changeFragment(new MainFragment());
            TilteTextView.setText("Mosque Information");
        }
        getSupportActionBar().hide();
        setup();
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
        FindMosque     = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_home,"Mosques");
        UpdateNamazTime  = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_profile,  "Namaz Time");
        Silent = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_calendar, "Silent");
        AboutUs = new com.special.ResideMenu.ResideMenuItem(this, R.drawable.icon_settings, "About Us");
        FindMosque.setMinimumWidth(50);

        // create menu items;
        FindMosque.setOnClickListener(this);
        UpdateNamazTime.setOnClickListener(this);
        Silent.setOnClickListener(this);
        AboutUs.setOnClickListener(this);

        resideMenu.addMenuItem(FindMosque,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(UpdateNamazTime,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(Silent,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(AboutUs,ResideMenu.DIRECTION_LEFT);


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
            changeFragment(new MainFragment());
            TilteTextView.setText("Mosque Information");
        }else
        if(v==UpdateNamazTime){
            TilteTextView.setText("Update Namaz Time");
            changeFragment(new UpdateNamazTime());
            UpdateNamazTime.startViewTransition(v);

            /*
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            if (settings.getString("logged", "").toString().equals("logged")) {
                changeFragment(new UpdateNamazTime());
            } else {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                changeFragment(new MainFragment());
            }*/
        }else
        if(v==Silent){
            Random r=new Random();
            int i=r.nextInt(5-1+1)+1;
            //Toast.makeText(MainActivity.this, ""+i, Toast.LENGTH_SHORT).show();
            TilteTextView.setText("Silent Scheduler");
            changeFragment(new SilentMobile());
            if(i==3||i==5)
            AdBuddiz.showAd(this);
        }else
        if(v==AboutUs){
            TilteTextView.setText("OneWindowSol");
            changeFragment(new About());
        }
        resideMenu.closeMenu();
    }
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
    public void onBackPressed()
    {
            if (resideMenu.isOpened()) {
                super.onBackPressed();
            }
            else{
                resideMenu.openMenu(0);
            }
    }
}