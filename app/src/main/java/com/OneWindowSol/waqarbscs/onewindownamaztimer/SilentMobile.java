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
public class SilentMobile extends Fragment implements  View.OnClickListener {

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

    public static String MY_PREFS = "MY_PREFS";
    private SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

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

        mySharedPreferences = getContext().getSharedPreferences(MY_PREFS, 0);
        String string1 = mySharedPreferences.getString("key1", "00:00");
        String string2 = mySharedPreferences.getString("key2", "00:00");
        String string3 = mySharedPreferences.getString("key3", "00:00");
        String string4 = mySharedPreferences.getString("key4", "00:00");
        String string5 = mySharedPreferences.getString("key5", "00:00");
        String string6 = mySharedPreferences.getString("key6", "00:00");
        String string7 = mySharedPreferences.getString("key7", "00:00");
        String string8 = mySharedPreferences.getString("key8", "00:00");
        String string9 = mySharedPreferences.getString("key9", "00:00");
        String string0 = mySharedPreferences.getString("key0", "00:00");

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

                editor = mySharedPreferences.edit();
                editor.putString("key1", fajarStart.getText().toString());
                editor.putString("key2", FajarEnd.getText().toString());
                editor.putString("key3", zuharStart.getText().toString());
                editor.putString("key4", zuharEnd.getText().toString());
                editor.putString("key5", asarStart.getText().toString());
                editor.putString("key6", asarEnd.getText().toString());
                editor.putString("key7", magribStart.getText().toString());
                editor.putString("key8", magribEnd.getText().toString());
                editor.putString("key9", EshaStart.getText().toString());
                editor.putString("key0", EshaEnd.getText().toString());
                editor.apply();

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
                                fajarStart.setText(hourOfDay + ":" + minute);
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
                                FajarEnd.setText(hourOfDay + ":" + minute);
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
                                zuharStart.setText(hourOfDay + ":" + minute);
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
                                zuharEnd.setText(hourOfDay + ":" + minute);
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
                                asarStart.setText(hourOfDay + ":" + minute);
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
                                asarEnd.setText(hourOfDay + ":" + minute);
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
                                magribStart.setText(hourOfDay + ":" + minute);
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
                                magribEnd.setText(hourOfDay + ":" + minute);
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
                                EshaEnd.setText(hourOfDay + ":" + minute);
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
                                EshaStart.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog9.show();
                break;

        }
    }

}
