package com.example.waqarbscs.onewindownamaztimer;


import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.example.waqarbscs.onewindownamaztimer.CustomServices.RestApiAccessService;
import com.example.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.example.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.special.ResideMenu.ResideMenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateNamazTime extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        PlaceSelectionListener, View.OnClickListener {

    String placeID;
    String name;
    LatLng latlng;
    String vicinity = "NA";
    public static final String PREFS_NAME = "LoginPrefs";

    int i=0;

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    String click;
    Masjids _masjids;
    private static final String TIME_PATTERN = "HH:mm";
    TextView textFajar, textZuhar, textAsar, textMagrib, textEsha, textJumma;
    Button button;
    ImageButton button2, button3, button4, button5, button6, button7;

    SharedPreferences settings;

    BroadcastReceiver mBroadCastReciever;

    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(24.9062, 67.184), new LatLng(24.9062, 67.184));
    private static final int REQUEST_SELECT_PLACE = 1;
    private TextView locationTextView;
    private TextView attributionsTextView;
    AutocompleteFilter typeFilter;
    View ParentView;

    private static final int MENU_ADD = Menu.FIRST;
    public UpdateNamazTime() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ParentView=inflater.inflate(R.layout.fragment_update_namaz_time, container, false);
        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        textFajar = (TextView)ParentView.findViewById(R.id.name);
        textZuhar = (TextView)ParentView.findViewById(R.id.name1);
        textAsar = (TextView)ParentView.findViewById(R.id.name2);
        textMagrib = (TextView)ParentView.findViewById(R.id.name3);
        textEsha = (TextView)ParentView.findViewById(R.id.name4);
        textJumma = (TextView)ParentView.findViewById(R.id.name5);
        FloatingActionButton fab = (FloatingActionButton)ParentView.findViewById(R.id.fab);

        //assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFajar.setText("00:00");
                textZuhar.setText("00:00");
                textAsar.setText("00:00");
                textMagrib.setText("00:00");
                textEsha.setText("00:00");
                textJumma.setText("00:00");

                // Method #2
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder
                            (PlaceAutocomplete.MODE_FULLSCREEN)
                            .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                            .build(getActivity());
                    startActivityForResult(intent, REQUEST_SELECT_PLACE);
                } catch (GooglePlayServicesRepairableException |
                        GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }

        });

        _masjids = new Masjids();

        button = (Button) ParentView.findViewById(R.id.button);
        button.setOnClickListener(this);

        button2 = (ImageButton) ParentView.findViewById(R.id.imgb_timers);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Fajar";
                TimePickerDialog.newInstance(UpdateNamazTime.this, calendar.get(Calendar.HOUR_OF_DAY),
                       calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
            }
        });
        button3 = (ImageButton) ParentView.findViewById(R.id.imgb_timers1);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Duhar";
                TimePickerDialog.newInstance(UpdateNamazTime.this, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");

            }
        });
        button4 = (ImageButton) ParentView.findViewById(R.id.imgb_timers2);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Asar";
                TimePickerDialog.newInstance(UpdateNamazTime.this, calendar.get(Calendar.HOUR_OF_DAY),
                       calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");

            }
        });
        button5 = (ImageButton) ParentView.findViewById(R.id.imgb_timers3);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Magrib";
                TimePickerDialog.newInstance(UpdateNamazTime.this, calendar.get(Calendar.HOUR_OF_DAY),
                       calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");

            }
        });
        button6 = (ImageButton) ParentView.findViewById(R.id.imgb_timers4);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Esha";
                TimePickerDialog.newInstance(UpdateNamazTime.this, calendar.get(Calendar.HOUR_OF_DAY),
                       calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
            }
        });

        button7 = (ImageButton) ParentView.findViewById(R.id.imgb_timers5);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "Juma";
                TimePickerDialog.newInstance(UpdateNamazTime.this, calendar.get(Calendar.HOUR_OF_DAY),
                      calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
            }
        });


        //Broadcast Receiver
        mBroadCastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().endsWith(RestApiAccessService.BroadCastActionSuccess)) {

                    Log.d("Intent", "Recived the broadcast");

                    //we know that we have successfully got the masjid data now we just have to show it
                    Masjids masjidRecieved = intent.getParcelableExtra("Masjid");

                    Log.d("Intent", "FajarTime: " + masjidRecieved.getFajarTime());

                    textFajar.setText(masjidRecieved.getFajarTime());
                    textZuhar.setText(masjidRecieved.getAsarTime());
                    textAsar.setText(masjidRecieved.getDoharTime());
                    textMagrib.setText(masjidRecieved.getMagribTime());
                    textEsha.setText(masjidRecieved.getEshaTime());
                    textJumma.setText(masjidRecieved.getJummaTime());

                } else if (intent.getAction().endsWith(RestApiAccessService.BroadCastActionError)) {
                    Toast.makeText(getActivity(), "Coundn't find any detail of this masjid.", Toast.LENGTH_LONG).show();
                }
            }
        };
        return ParentView;
    }
    public boolean IsReadyToFindMasjids() {


        if (!AppManager.getInstance().isInternetAvailable()) {
            Toast.makeText(getActivity(), "Please connect to intternet.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void getMasjidDetail() {
        Intent intent = new Intent(getActivity(), RestApiAccessService.class);
        intent.putExtra("Action", "getAllMasjids");
        intent.putExtra("PlaceId", placeID);
        Log.d("Service", "Starting the service.");
        getContext().startService(intent);
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
            case "Fajar":
                textFajar.setText(timeFormat.format(calendar.getTime()));
                break;
            case "Duhar":
                textZuhar.setText(timeFormat.format(calendar.getTime()));
                break;
            case "Asar":
                textAsar.setText(timeFormat.format(calendar.getTime()));
                break;
            case "Magrib":
                textMagrib.setText(timeFormat.format(calendar.getTime()));
                break;
            case "Esha":
                textEsha.setText(timeFormat.format(calendar.getTime()));
                break;
            case "Juma":
                textJumma.setText(timeFormat.format(calendar.getTime()));
                break;
        }
    }

    boolean conditon = false;

    @Override
    public void onPlaceSelected(Place place) {


        Log.i(LOG_TAG, "Place Selected: " + place.getName());
        /*locationTextView.setText(getString(R.string.formatted_place_data, place
                .getName(), place.getAddress(), place.getPhoneNumber(), place
                .getWebsiteUri(), place.getRating(), place.getId()));
        */
        boolean a = place.getName().toString().contains("Mosque");
        boolean b = place.getName().toString().contains("Masjid");
        boolean c = place.getName().toString().contains("mosque");
        boolean d = place.getName().toString().contains("masjid");
        if(a||b||c||d) {
            placeID = place.getId();
            name = place.getName().toString();
            latlng = place.getLatLng();
            _masjids.setMasjidName(name);
            _masjids.setPlaceId(placeID);
            _masjids.setVacinity(vicinity);
            _masjids.setLatLong(latlng);
            button.setVisibility(View.VISIBLE);
            getMasjidDetail();
            conditon = true;
            //Toast.makeText(getActivity(), "Please select a mosque", Toast.LENGTH_LONG).show();
        }else if (!TextUtils.isEmpty(place.getAttributions())) {
            attributionsTextView.setText(Html.fromHtml(place.getAttributions().toString()));
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadCastReciever, new IntentFilter(RestApiAccessService.BroadCastActionSuccess));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadCastReciever, new IntentFilter(RestApiAccessService.BroadCastActionError));

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadCastReciever);
    }

    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(getActivity(), "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //builder.setTitle("Location Services Not Active");
                builder.setMessage("Want to update Time?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intentService = new Intent(getActivity(), RestApiAccessService.class);
                        _masjids.setFajarTime(textFajar.getText().toString());
                        _masjids.setDoharTime(textZuhar.getText().toString());
                        _masjids.setAsarTime(textAsar.getText().toString());
                        _masjids.setMagribTime(textMagrib.getText().toString());
                        _masjids.setEshaTime(textEsha.getText().toString());
                        _masjids.setJummaTime(textJumma.getText().toString());
                        intentService.putExtra("Masjid", _masjids);
                        intentService.putExtra("Action", "update");
                        Log.d("Service", "Starting the service.");
                        getActivity().startService(intentService);

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
                break;
        }

    }

}
