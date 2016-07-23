package com.OneWindowSol.waqarbscs.onewindownamaztimer;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Azan.AzanService;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Database.hadithDatabase;
import android.database.Cursor;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Models.Timings;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.util.Random;
import android.database.sqlite.SQLiteDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateNamazTime extends Fragment implements CompoundButton.OnCheckedChangeListener{

    hadithDatabase hd;
    //SQLiteDatabase sql;

    View ParentView;
    public String API="http://api.aladhan.com/timingsByCity?city=karachi&country=pakistan&method=1";
    TextView textFajar, textZuhar, textAsar, textMagrib, textEsha,texthadith;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    PendingIntent pintent;
    ArrayList<String> values;
    String fajar,dhuhr,Asr,Maghrib,Isha;

    Button abc;

    public static String MY_PREFS = "MY_PREFS";
    private SharedPreferences mySharedPreferences,mySharedPreference1;
    SharedPreferences.Editor editor,editor1;


    public UpdateNamazTime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ParentView=inflater.inflate(R.layout.fragment_update_namaz_time, container, false);



        hd=new hadithDatabase(getContext());
        textFajar = (TextView)ParentView.findViewById(R.id.name);
        textZuhar = (TextView)ParentView.findViewById(R.id.name1);
        textAsar = (TextView)ParentView.findViewById(R.id.name2);
        textMagrib = (TextView)ParentView.findViewById(R.id.name3);
        textEsha = (TextView)ParentView.findViewById(R.id.name4);
        texthadith=(TextView)ParentView.findViewById(R.id.textView6);

        abc=(Button)ParentView.findViewById(R.id.add);

        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });

        Calendar c = Calendar.getInstance();
        int hour=c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int am_pm=c.get(Calendar.AM_PM);
        String time=(am_pm==Calendar.AM)?"am":"pm";
        Log.d("ampm",time);
        String DhuhrHadith="جس نے  ظہر کی نماز ترک کی،اس کی روزی سے برکت ختم ہوجاتی ہے.";
        String AsarHadith="جس نے عصر کی ماز ترک کی،اس کے جسم سے طاقت ختم ہو جاتی ہے.";
        String MaghribHadith="جس نے مغرب کی نماز ترک کی ، اس کو اولاد سے کوئی خوشی حاصل نہیں ہوگی.";
        String EshaHadith="جس نے عشاء کی نماز ترک کی، اس کی نیند سے راحت ختم ہوجاتی ہے.";
        texthadith.setText("جس نے فجر کی نماز ترک کی، اس کے چہرے سے نور ختم کر دیا جاتا ہے.");

        mySharedPreferences = getContext().getSharedPreferences(MY_PREFS, 0);

         if (hour >= 1 &&time=="pm") {
             texthadith.setText(DhuhrHadith);
         }
        if(hour>=5&&time=="pm"){
            texthadith.setText(AsarHadith);
        }
        if(hour>=7&&time=="pm"){
            texthadith.setText(MaghribHadith);
        }
        if(hour>=8&&time=="pm"){
            texthadith.setText(EshaHadith);
        }

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

        if (AppManager.getInstance().isInternetAvailable()) {
            new NamazTimes().execute(API);
        }
        else{

            textFajar.setText(mySharedPreferences.getString("key1", "00:00"));
            textZuhar.setText(mySharedPreferences.getString("key2", "00:00"));
            textAsar.setText(mySharedPreferences.getString("key3", "00:00"));
            textMagrib.setText(mySharedPreferences.getString("key4", "00:00"));
            textEsha.setText(mySharedPreferences.getString("key5", "00:00"));
            //Toast.makeText(getActivity(), "Please enable internet", Toast.LENGTH_SHORT).show();
        }
        mySharedPreferences = getContext().getSharedPreferences(MY_PREFS, 0);
        boolean fajarboolean = mySharedPreferences.getBoolean("fajarCheck", false);
        boolean dhuhrboolean = mySharedPreferences.getBoolean("dhuhrCheck", false);
        boolean asarboolean = mySharedPreferences.getBoolean("asarCheck", false);
        boolean maghribboolean = mySharedPreferences.getBoolean("maghribCheck", false);
        boolean ishaboolean = mySharedPreferences.getBoolean("ishaCheck", false);

      // boolean df=hd.insertdata("یہ انگریزی نہیں");

       // Toast.makeText(getActivity(), "data inserted", Toast.LENGTH_SHORT).show();
        /*
        Cursor res=hd.getData("13");
        String hadith="";
        while(res.moveToNext()) {
            Log.d("names", res.getString(res.getColumnIndex("NAME")));
            hadith=res.getString(res.getColumnIndex("NAME"));
        }
        */
        //texthadith.setSelected(true);

        if(fajarboolean)
            checkBox1.setChecked(true );
        if(dhuhrboolean)
            checkBox2.setChecked(true);
        if(asarboolean)
            checkBox3.setChecked(true);
        if(maghribboolean)
            checkBox4.setChecked(true);
        if(ishaboolean)
            checkBox5.setChecked(true);
        return ParentView;
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        final  EditText edt1=(EditText)dialogView.findViewById(R.id.edit2);
        dialogBuilder.setTitle("Select City And Country Name");
        //dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String a=edt.getText().toString();
                String b=edt1.getText().toString();

                if (AppManager.getInstance().isInternetAvailable()) {
                    if(a!=null&&b!=null) {
                        new NamazTimes().execute("http://api.aladhan.com/timingsByCity?city=" + a + "&country=" + b + "&method=1");
                    }
                    else {
                        Toast.makeText(getActivity(), "Please set city and country name", Toast.LENGTH_SHORT).show();
                    }
                    }
                else{
                    Toast.makeText(getActivity(), "Internet is not avaialbe", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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
        int prefMode = Activity.MODE_PRIVATE;

        editor = mySharedPreferences.edit();

        switch (buttonView.getId()){
            case R.id.checkBox1:
                fajarCheck=isChecked;
                if(!fajarCheck){
                    CancelAlarm(getContext());
                    Toast.makeText(getActivity(), "Fajar Alarm off", Toast.LENGTH_SHORT).show();
                }
                    editor.putBoolean("fajarCheck",fajarCheck);
                    editor.apply();

                break;
            case R.id.checkBox2:
                dhuhrCheck=isChecked;
                if(!dhuhrCheck){

                    CancelAlarm(getContext());
                    Toast.makeText(getActivity(), "Dhuhr Alarm off", Toast.LENGTH_SHORT).show();
                }
                    editor.putBoolean("dhuhrCheck",dhuhrCheck);
                    editor.apply();


                break;
            case R.id.checkBox3:
                asarCheck=isChecked;
                if(!asarCheck){
                    CancelAlarm(getContext());
                    Toast.makeText(getActivity(), "Asr Alarm off", Toast.LENGTH_SHORT).show();
                }
                    editor.putBoolean("asarCheck",asarCheck);
                    editor.apply();

                break;
            case R.id.checkBox4:
                maghribCheck=isChecked;
                if(!maghribCheck){
                    CancelAlarm(getContext());
                    Toast.makeText(getActivity(), "Maghrib Alarm off", Toast.LENGTH_SHORT).show();
                }
                    editor.putBoolean("maghribCheck",maghribCheck);
                    editor.apply();

                break;
            case R.id.checkBox5:
                ishaCheck=isChecked;
                if(!ishaCheck){
                    CancelAlarm(getContext());
                    Toast.makeText(getActivity(), "Esha Alarm off", Toast.LENGTH_SHORT).show();
                }
                    editor.putBoolean("ishaCheck",ishaCheck);
                    editor.apply();

                break;
        }
        if (checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked()||checkBox5.isChecked()) {

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
           // Toast.makeText(getActivity(), "Start Azan Alarm", Toast.LENGTH_SHORT).show();
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
        fajar=jsonTiming.getString("Fajr");
        dhuhr=jsonTiming.getString("Dhuhr");
        Asr=jsonTiming.getString("Asr");
        Maghrib=jsonTiming.getString("Maghrib");
        Isha=jsonTiming.getString("Isha");


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

        editor = mySharedPreferences.edit();
        editor.putString("key1", fajar.toString());
        editor.putString("key2", dhuhr.toString());
        editor.putString("key3", Asr.toString());
        editor.putString("key4", Maghrib.toString());
        editor.putString("key5", Isha.toString());
        editor.apply();
    }
}
