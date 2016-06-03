package com.example.waqarbscs.onewindownamaztimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waqarbscs.onewindownamaztimer.CustomServices.RestApiAccessService;
import com.example.waqarbscs.onewindownamaztimer.DirectionClasses.DirectionFinder;
import com.example.waqarbscs.onewindownamaztimer.Models.Masjids;


public class MasjidDetailPage extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    private static final String EXTRA_TITLE = "com.antonioleiva.materializeyourapp.extraTitle";

    String placeid;
    String masjidname;

    private TextView txtVMasjidName;

    DirectionFinder directionFinder;


    BroadcastReceiver mBroadCastReciever;
    TextView txtVfarjarTime;
    TextView txtVdoharTime;
    TextView txtVasarTime;
    TextView txtVmagribTime;
    TextView txtVeshaTime;
    TextView txtVJummaTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_detail_page);

        Intent testIntent = getIntent();

        placeid = testIntent.getStringExtra("PlaceId");
        masjidname = testIntent.getStringExtra("MasjidName");


        txtVfarjarTime = (TextView) findViewById(R.id.txtv_fajarTime);
        txtVdoharTime = (TextView) findViewById(R.id.txtv_doharTime);
        txtVasarTime = (TextView) findViewById(R.id.txtv_asarTime);
        txtVmagribTime = (TextView) findViewById(R.id.txtv_magribTime);
        txtVeshaTime = (TextView) findViewById(R.id.txtv_eshaTime);

        txtVJummaTime = (TextView) findViewById(R.id.txtv_jummaTime);

        mBroadCastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().endsWith(RestApiAccessService.BroadCastActionSuccess)) {

                    Log.d("Intent", "Recived the broadcast");

                    //we know that we have successfully got the masjid data now we just have to show it
                    Masjids masjidRecieved = intent.getParcelableExtra("Masjid");

                    Log.d("Intent", "FajarTime: " + masjidRecieved.getFajarTime());

                    txtVfarjarTime.setText(masjidRecieved.getFajarTime());
                    txtVasarTime.setText(masjidRecieved.getAsarTime());
                    txtVdoharTime.setText(masjidRecieved.getDoharTime());
                    txtVmagribTime.setText(masjidRecieved.getMagribTime());
                    txtVeshaTime.setText(masjidRecieved.getEshaTime());
                    txtVJummaTime.setText(masjidRecieved.getJummaTime());

                } else if (intent.getAction().endsWith(RestApiAccessService.BroadCastActionError)) {
                    Toast.makeText(MasjidDetailPage.this, "Coundn't find any detail of this masjid.", Toast.LENGTH_LONG).show();
                }
            }
        };

        //well with that we also have to search for the detail of the masjid
        getMasjidDetail();

        txtVMasjidName = (TextView) findViewById(R.id.txtv_masjidName);
        txtVMasjidName.setText(masjidname);

    }

    public void getMasjidDetail() {
        Intent intent = new Intent(MasjidDetailPage.this, RestApiAccessService.class);
        intent.putExtra("Action", "getAllMasjids");
        intent.putExtra("PlaceId", placeid);
        Log.d("Service", "Starting the service.");
        this.startService(intent);
    }

    @Override
    protected void onResume() {

        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadCastReciever, new IntentFilter(RestApiAccessService.BroadCastActionSuccess));
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadCastReciever, new IntentFilter(RestApiAccessService.BroadCastActionError));

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadCastReciever);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

}
