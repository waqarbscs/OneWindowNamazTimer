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
public class SilentMobile extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

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
        String string1 = mySharedPreferences.getString("key1", null);
        String string2 = mySharedPreferences.getString("key2", null);
        String string3 = mySharedPreferences.getString("key3", null);
        String string4 = mySharedPreferences.getString("key4", null);
        String string5 = mySharedPreferences.getString("key5", null);
        String string6 = mySharedPreferences.getString("key6", null);
        String string7 = mySharedPreferences.getString("key7", null);
        String string8 = mySharedPreferences.getString("key8", null);
        String string9 = mySharedPreferences.getString("key9", null);
        String string0 = mySharedPreferences.getString("key0", null);


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
                builder.setMessage("Want To start Service");
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
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update(click);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update(click);
    }

    private void update(String s) {
        switch (s) {
            case "FajarStart":
                fajarStart.setText(timeFormat.format(calendar.getTime()));
                break;
            case "FajarEnd":
                FajarEnd.setText(timeFormat.format(calendar.getTime()));
                break;
            case "ZuharStart":
                zuharStart.setText(timeFormat.format(calendar.getTime()));
                break;
            case "ZuharEnd":
                zuharEnd.setText(timeFormat.format(calendar.getTime()));
                break;
            case "AsarStart":
                asarStart.setText(timeFormat.format(calendar.getTime()));
                break;
            case "AsarEnd":
                asarEnd.setText(timeFormat.format(calendar.getTime()));
                break;
            case "MagribStart":
                magribStart.setText(timeFormat.format(calendar.getTime()));
                break;
            case "MagribEnd":
                magribEnd.setText(timeFormat.format(calendar.getTime()));
                break;
            case "EshaStart":
                EshaStart.setText(timeFormat.format(calendar.getTime()));
                break;
            case "EshaEnd":
                EshaEnd.setText(timeFormat.format(calendar.getTime()));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FajarStart:
                click = "FajarStart";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.FajarEnd:
                click = "FajarEnd";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.ZuharStart:
                click = "ZuharStart";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.ZuharEnd:
                click = "ZuharEnd";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.AsarStart:
                click = "AsarStart";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.AsarEnd:
                click = "AsarEnd";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.MagribStart:
                click = "MagribStart";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                       calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.MagribEnd:
                click = "MagribEnd";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.EshaStart:
                click = "EshaStart";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;
            case R.id.EshaEnd:
                click = "EshaEnd";
                TimePickerDialog.newInstance(SilentMobile.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
                break;

        }
    }

}
