package com.OneWindowSol.waqarbscs.onewindownamaztimer;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.SilentService.silentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SilentMobile extends Fragment implements  View.OnClickListener, View.OnLongClickListener {

    View ParentView;
    Calendar calendar;
    private static final String TIME_PATTERN = "HH:mm";
    private SimpleDateFormat timeFormat;
    String click;

    TextView fajarStart, FajarEnd, zuharStart, zuharEnd, asarStart, asarEnd, magribStart, magribEnd, EshaStart, EshaEnd;

    PendingIntent pintent;

    String[] abc;
    ArrayList<String> values;
    Button buttonStart, buttonStop;

    public static String MY_PREFS = "silentPrefernece";
    private SharedPreferences silentSharedPreferences;
    SharedPreferences.Editor silenteditor;

    private int  mHour, mMinute;

    public SilentMobile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ParentView=inflater.inflate(R.layout.fragment_silent_mobile, container, false);
        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        abc = new String[10];
        values = new ArrayList<>();

        //Initializing
        buttonStart = (Button) ParentView.findViewById(R.id.buttonStart);
        //buttonStop=(Button)findViewById(R.id.buttonStop);
        fajarStart = (TextView) ParentView.findViewById(R.id.FajarStart);
        FajarEnd = (TextView) ParentView.findViewById(R.id.FajarEnd);
        zuharStart = (TextView) ParentView.findViewById(R.id.ZuharStart);
        zuharEnd = (TextView) ParentView.findViewById(R.id.ZuharEnd);
        asarStart = (TextView) ParentView.findViewById(R.id.AsarStart);
        asarEnd = (TextView) ParentView.findViewById(R.id.AsarEnd);
        magribStart = (TextView) ParentView.findViewById(R.id.MagribStart);
        magribEnd = (TextView) ParentView.findViewById(R.id.MagribEnd);
        EshaStart = (TextView) ParentView.findViewById(R.id.EshaStart);
        EshaEnd = (TextView) ParentView.findViewById(R.id.EshaEnd);

        silentSharedPreferences = getContext().getSharedPreferences(MY_PREFS, 0);
        String string1 = silentSharedPreferences.getString("key1", "00:00");
        String string2 = silentSharedPreferences.getString("key2", "00:00");
        String string3 = silentSharedPreferences.getString("key3", "00:00");
        String string4 = silentSharedPreferences.getString("key4", "00:00");
        String string5 = silentSharedPreferences.getString("key5", "00:00");
        String string6 = silentSharedPreferences.getString("key6", "00:00");
        String string7 = silentSharedPreferences.getString("key7", "00:00");
        String string8 = silentSharedPreferences.getString("key8", "00:00");
        String string9 = silentSharedPreferences.getString("key9", "00:00");
        String string0 = silentSharedPreferences.getString("key0", "00:00");

        if (string1 != null) {
            fajarStart.setText(string1);
            FajarEnd.setText(string2);
            zuharStart.setText(string3);
            zuharEnd.setText(string4);
            asarStart.setText(string5);
            asarEnd.setText(string6);
            magribStart.setText(string7);
            magribEnd.setText(string8);
            EshaStart.setText(string9);
            EshaEnd.setText(string0);
        }
/*
        values.add(fajarStart.getText().toString());
        values.add(FajarEnd.getText().toString());
        values.add(zuharStart.getText().toString());
        values.add(zuharEnd.getText().toString());
        values.add(asarStart.getText().toString());
        values.add(asarEnd.getText().toString());
        values.add(magribStart.getText().toString());
        values.add(magribEnd.getText().toString());
        values.add(EshaStart.getText().toString());
        values.add(EshaEnd.getText().toString());
*/

        //TextView Event
        fajarStart.setOnClickListener(this);
        FajarEnd.setOnClickListener(this);
        zuharEnd.setOnClickListener(this);
        zuharStart.setOnClickListener(this);
        asarStart.setOnClickListener(this);
        asarEnd.setOnClickListener(this);
        magribStart.setOnClickListener(this);
        magribEnd.setOnClickListener(this);
        EshaStart.setOnClickListener(this);
        EshaEnd.setOnClickListener(this);

        fajarStart.setOnLongClickListener(this);
        FajarEnd.setOnLongClickListener(this);
        zuharEnd.setOnLongClickListener(this);
        zuharStart.setOnLongClickListener(this);
        asarEnd.setOnLongClickListener(this);
        asarStart.setOnLongClickListener(this);
        magribEnd.setOnLongClickListener(this);
        magribStart.setOnLongClickListener(this);
        EshaStart.setOnLongClickListener(this);
        EshaEnd.setOnLongClickListener(this);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.clear();
                values.add(fajarStart.getText().toString());
                values.add(FajarEnd.getText().toString());
                values.add(zuharStart.getText().toString());
                values.add(zuharEnd.getText().toString());
                values.add(asarStart.getText().toString());
                values.add(asarEnd.getText().toString());
                values.add(magribStart.getText().toString());
                values.add(magribEnd.getText().toString());
                values.add(EshaStart.getText().toString());
                values.add(EshaEnd.getText().toString());


                int prefMode = Activity.MODE_PRIVATE;

                silenteditor = silentSharedPreferences.edit();
                silenteditor.putString("key1", fajarStart.getText().toString());
                silenteditor.putString("key2", FajarEnd.getText().toString());
                silenteditor.putString("key3", zuharStart.getText().toString());
                silenteditor.putString("key4", zuharEnd.getText().toString());
                silenteditor.putString("key5", asarStart.getText().toString());
                silenteditor.putString("key6", asarEnd.getText().toString());
                silenteditor.putString("key7", magribStart.getText().toString());
                silenteditor.putString("key8", magribEnd.getText().toString());
                silenteditor.putString("key9", EshaStart.getText().toString());
                silenteditor.putString("key0", EshaEnd.getText().toString());
                silenteditor.apply();

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }

                //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //builder.setTitle("Location Services Not Active");
                builder.setMessage("Want To Start Namaz Service");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, 10);
                        Intent intent = new Intent(getActivity(), silentService.class);
                        intent.putStringArrayListExtra("abc", values);
                        Random rand = new Random(cal.getTimeInMillis());
                        pintent = PendingIntent.getService(getContext(), rand.nextInt(), intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarm = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                                1000 * 20, pintent);
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
        });

        return ParentView;
    }
    public void CancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Activity.ALARM_SERVICE);

        alarmManager.cancel(pintent);
    }



    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);

        mMinute = c.get(Calendar.MINUTE);
        switch (v.getId()) {
            case R.id.FajarStart:
                click = "FajarStart";

                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    fajarStart.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    fajarStart.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    fajarStart.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    fajarStart.setText(hourOfDay + ":" + minute);
                                }


                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.FajarEnd:
                click = "FajarEnd";
                android.app.TimePickerDialog timePickerDialog1 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    FajarEnd.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    FajarEnd.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    FajarEnd.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    FajarEnd.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog1.show();
                break;
            case R.id.ZuharStart:
                click = "ZuharStart";
                android.app.TimePickerDialog timePickerDialog2 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    zuharStart.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    zuharStart.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    zuharStart.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    zuharStart.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog2.show();
                break;
            case R.id.ZuharEnd:
                click = "ZuharEnd";
                android.app.TimePickerDialog timePickerDialog3 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    zuharEnd.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    zuharEnd.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    zuharEnd.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    zuharEnd.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog3.show();
                break;
            case R.id.AsarStart:
                click = "AsarStart";
                android.app.TimePickerDialog timePickerDialog4 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    asarStart.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    asarStart.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    asarStart.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    asarStart.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog4.show();
                break;
            case R.id.AsarEnd:
                click = "AsarEnd";
                android.app.TimePickerDialog timePickerDialog5 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    asarEnd.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    asarEnd.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    asarEnd.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    asarEnd.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog5.show();
                break;
            case R.id.MagribStart:
                click = "MagribStart";
                android.app.TimePickerDialog timePickerDialog6 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    magribStart.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    magribStart.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    magribStart.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    magribStart.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog6.show();
                break;
            case R.id.MagribEnd:
                click = "MagribEnd";
                android.app.TimePickerDialog timePickerDialog7 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    magribEnd.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    magribEnd.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    magribEnd.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    magribEnd.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog7.show();
                break;
            case R.id.EshaStart:
                click = "EshaStart";
                android.app.TimePickerDialog timePickerDialog8 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    EshaStart.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    EshaStart.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    EshaStart.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    EshaStart.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog8.show();
                break;
            case R.id.EshaEnd:
                click = "EshaEnd";
                android.app.TimePickerDialog timePickerDialog9 = new android.app.TimePickerDialog(getActivity(),
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(hourOfDay<10&&minute<10){
                                    EshaEnd.setText("0"+hourOfDay + ":" + "0"+minute);
                                }else if(hourOfDay<10&&minute>=10){
                                    EshaEnd.setText("0"+hourOfDay + ":" + minute);
                                }else if(hourOfDay>=10&&minute<10){
                                    EshaEnd.setText(hourOfDay + ":" +"0"+ minute);
                                }else {
                                    EshaEnd.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog9.show();
                break;

        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.FajarStart:
                fajarStart.setText("00:00");
                break;
            case R.id.FajarEnd:
               FajarEnd.setText("00:00");
                break;
            case R.id.ZuharStart:
                zuharStart.setText("00:00");
                break;
            case R.id.ZuharEnd:
                zuharEnd.setText("00:00");
                break;
            case R.id.AsarStart:
                asarStart.setText("00:00");
                break;
            case R.id.AsarEnd:
                asarEnd.setText("00:00");
                break;
            case R.id.MagribStart:
                magribStart.setText("00:00");
                break;
            case R.id.MagribEnd:
                magribEnd.setText("00:00");
                break;
            case R.id.EshaStart:
                EshaStart.setText("00:00");
                break;
            case R.id.EshaEnd:
                EshaEnd.setText("00:00");
                break;

        }
        return true;
    }
}
