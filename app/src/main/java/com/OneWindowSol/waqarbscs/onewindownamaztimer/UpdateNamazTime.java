package com.OneWindowSol.waqarbscs.onewindownamaztimer;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Azan.AzanService;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Models.Timings;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.SilentService.silentService;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.CustomServices.RestApiAccessService;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateNamazTime extends Fragment implements CompoundButton.OnCheckedChangeListener {

    View ParentView;
    public String API="http://api.aladhan.com/timingsByCity?city=karachi&country=pakistan&method=1";
    TextView textFajar, textZuhar, textAsar, textMagrib, textEsha;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    PendingIntent pintent;
    ArrayList<String> values;

    public UpdateNamazTime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ParentView=inflater.inflate(R.layout.fragment_update_namaz_time, container, false);
        new NamazTimes().execute(API);
        textFajar = (TextView)ParentView.findViewById(R.id.name);
        textZuhar = (TextView)ParentView.findViewById(R.id.name1);
        textAsar = (TextView)ParentView.findViewById(R.id.name2);
        textMagrib = (TextView)ParentView.findViewById(R.id.name3);
        textEsha = (TextView)ParentView.findViewById(R.id.name4);

        checkBox1=(CheckBox)ParentView.findViewById(R.id.checkBox1);
        checkBox1.setOnCheckedChangeListener(this);

        checkBox2=(CheckBox)ParentView.findViewById(R.id.checkBox2);
        checkBox2.setOnCheckedChangeListener(this);

        checkBox3=(CheckBox)ParentView.findViewById(R.id.checkBox3);
        checkBox3.setOnCheckedChangeListener(this);

        checkBox4=(CheckBox)ParentView.findViewById(R.id.checkBox4);
        checkBox4.setOnCheckedChangeListener(this);

        checkBox5=(CheckBox)ParentView.findViewById(R.id.checkBox5);
        checkBox5.setOnCheckedChangeListener(this);

        values=new ArrayList<>();

        return ParentView;
    }

    public void CancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Activity.ALARM_SERVICE);

        alarmManager.cancel(pintent);
    }
    boolean fajarCheck=false;
    boolean dhuhrCheck=false;
    boolean asarCheck=fajarCheck;
    boolean maghribCheck=false;
    boolean ishaCheck=false;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkBox1:
                fajarCheck=isChecked;
                break;
            case R.id.checkBox2:
                dhuhrCheck=isChecked;
                break;
            case R.id.checkBox3:
                asarCheck=isChecked;
                break;
            case R.id.checkBox4:
                maghribCheck=isChecked;
                break;
            case R.id.checkBox5:
                ishaCheck=isChecked;
                break;
        }
        if (checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked()||checkBox5.isChecked()) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            if(values.isEmpty()){
                values.add(textFajar.getText().toString());
                values.add(textZuhar.getText().toString());
                values.add(textAsar.getText().toString());
                values.add(textMagrib.getText().toString());
                values.add(textEsha.getText().toString());
            }
            Log.d("values2",values.toString());
            //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //builder.setTitle("Location Services Not Active");
            builder.setMessage("Want To start Service");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, 10);
                    Intent intent = new Intent(getActivity(), AzanService.class);
                    intent.putStringArrayListExtra("abc", values);
                    intent.putExtra("fajarCheck",fajarCheck);
                    intent.putExtra("dhuhrCheck",dhuhrCheck);
                    intent.putExtra("asarCheck",asarCheck);
                    intent.putExtra("maghribCheck",maghribCheck);
                    intent.putExtra("ishaCheck",ishaCheck);

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

        }else if(!checkBox1.isChecked()||!checkBox2.isChecked()||!checkBox3.isChecked()||!checkBox4.isChecked()||!checkBox5.isChecked()){
            CancelAlarm(getContext());
        }
    }

    private class NamazTimes extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            try {
                Log.d("rrs",s);
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String link=params[0];
            try {
                URL url = new URL(link);
                url.openConnection().setConnectTimeout(10000); //setting
                InputStream is = url.openConnection().getInputStream();


                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                Log.d("buffer", buffer.toString());
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private void parseJson(String data) throws JSONException {
        List<Timings> timing=new ArrayList<>();
        if(data==null)
            return;
        String as="";
        JSONObject jsonData=new JSONObject(data);

        //JSONArray jsonArray=jsonData.getJSONArray("data");
        JSONObject jsonData1=jsonData.getJSONObject("data");
        JSONObject jsonTiming=jsonData1.getJSONObject("timings");
        //JSONObject jsonFajar=jsonTiming.getJSONObject("Fajr");
        String fajar=jsonTiming.getString("Fajr");
        String dhuhr=jsonTiming.getString("Dhuhr");
        String Asr=jsonTiming.getString("Asr");
        String Maghrib=jsonTiming.getString("Maghrib");
        String Isha=jsonTiming.getString("Isha");

        textFajar.setText(fajar);
        textZuhar.setText(dhuhr);
        textAsar.setText(Asr);
        textMagrib.setText(Maghrib);
        textEsha.setText(Isha);
        values.clear();
        values.add(textFajar.getText().toString());
        values.add(textZuhar.getText().toString());
        values.add(textAsar.getText().toString());
        values.add(textMagrib.getText().toString());
        values.add(textEsha.getText().toString());
    }
}
