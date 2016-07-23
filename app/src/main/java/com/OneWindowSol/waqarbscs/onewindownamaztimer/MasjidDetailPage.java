package com.OneWindowSol.waqarbscs.onewindownamaztimer;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.CustomServices.RestApiAccessService;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.DirectionClasses.DirectionFinder;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MasjidDetailPage extends AppCompatActivity implements  View.OnClickListener {

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

    ImageView image1,image2,image3,image4,image5,image6;


    String click;
    private Calendar calendar;
    private static final String TIME_PATTERN = "HH:mm";
    private SimpleDateFormat timeFormat;

    ImageButton imageButton;

    Masjids _masjids;

    private int  mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_detail_page);

        Intent testIntent = getIntent();

        placeid = testIntent.getStringExtra("PlaceId");
        masjidname = testIntent.getStringExtra("MasjidName");

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        txtVfarjarTime = (TextView) findViewById(R.id.txtv_fajarTime);
        txtVdoharTime = (TextView) findViewById(R.id.txtv_doharTime);
        txtVasarTime = (TextView) findViewById(R.id.txtv_asarTime);
        txtVmagribTime = (TextView) findViewById(R.id.txtv_magribTime);
        txtVeshaTime = (TextView) findViewById(R.id.txtv_eshaTime);
        txtVJummaTime = (TextView) findViewById(R.id.txtv_jummaTime);


        image1=(ImageView)findViewById(R.id.image1);
        image2=(ImageView)findViewById(R.id.image2);
        image3=(ImageView)findViewById(R.id.image3);
        image4=(ImageView)findViewById(R.id.image4);
        image5=(ImageView)findViewById(R.id.image5);
        image6=(ImageView)findViewById(R.id.image6);

        imageButton=(ImageButton)findViewById(R.id.imagebutton);

        imageButton.setOnClickListener(this);

        _masjids = new Masjids();
        _masjids.setMasjidName(masjidname);
        _masjids.setPlaceId(placeid);

        txtVMasjidName = (TextView) findViewById(R.id.txtv_masjidName);
        txtVMasjidName.setText(masjidname);
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Fajar";
                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(MasjidDetailPage.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtVfarjarTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                imageButton.setVisibility(View.VISIBLE);

            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Duhar";
                android.app.TimePickerDialog timePickerDialog1 = new android.app.TimePickerDialog(MasjidDetailPage.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtVdoharTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog1.show();
                imageButton.setVisibility(View.VISIBLE);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Asar";
                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(MasjidDetailPage.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtVasarTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                imageButton.setVisibility(View.VISIBLE);
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Magrib";
                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(MasjidDetailPage.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtVmagribTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                imageButton.setVisibility(View.VISIBLE);
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Esha";
                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(MasjidDetailPage.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtVeshaTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                imageButton.setVisibility(View.VISIBLE);
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Juma";
                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(MasjidDetailPage.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtVJummaTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                imageButton.setVisibility(View.VISIBLE);
            }
        });

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
                    Toast.makeText(MasjidDetailPage.this, "Coundn't find any detail of this Mosque.", Toast.LENGTH_LONG).show();
                    txtVfarjarTime.setText("00:00");
                    txtVdoharTime.setText("00:00");
                    txtVasarTime.setText("00:00");
                    txtVmagribTime.setText("00:00");
                    txtVeshaTime.setText("00:00");
                    txtVJummaTime.setText("00:00");

                }
            }
        };

        //well with that we also have to search for the detail of the masjid
        getMasjidDetail();


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



    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Location Services Not Active");
        builder.setMessage("Want to update Time?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intentService = new Intent(MasjidDetailPage.this, RestApiAccessService.class);
                _masjids.setFajarTime(txtVfarjarTime.getText().toString());
                _masjids.setDoharTime(txtVdoharTime.getText().toString());
                _masjids.setAsarTime(txtVasarTime.getText().toString());
                _masjids.setMagribTime(txtVmagribTime.getText().toString());
                _masjids.setEshaTime(txtVeshaTime.getText().toString());
                _masjids.setJummaTime(txtVJummaTime.getText().toString());
                intentService.putExtra("Masjid", _masjids);
                intentService.putExtra("Action", "update");
                Log.d("Service", "Starting the service.");
                startService(intentService);
                Toast.makeText(MasjidDetailPage.this, "Time Updated", Toast.LENGTH_SHORT).show();

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
