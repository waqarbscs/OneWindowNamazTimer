package com.OneWindowSol.waqarbscs.onewindownamaztimer.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.OneWindowSol.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.R;

import java.util.List;

/**
 * Created by Imdad on 4/27/2016.
 */
public class CustomMasjidListAdapter extends BaseAdapter {


    //variables part
    private LayoutInflater _layoutInflater;
    private Activity _activity;
    private List<Masjids> _masjids;

    public CustomMasjidListAdapter(Activity pActivity, List<Masjids> pMasjids) {

        this._activity = pActivity;
        this._masjids = pMasjids;
    }


    @Override
    public int getCount() {
        if (_masjids == null)
            return 0;

        return _masjids.size();
    }

    @Override
    public Object getItem(int position) {

        if (_masjids == null)
            return null;

        return _masjids.get(position);
    }

    @Override
    public long getItemId(int position) {
        //for now we are sending location of the itme as item id

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (_layoutInflater == null)
            _layoutInflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = _layoutInflater.inflate(R.layout.list_masjid_row, null);

        TextView txtvMasjidName = (TextView) convertView.findViewById(R.id.name); //name basicaly
        TextView txtvLatitude = (TextView) convertView.findViewById(R.id.latitude);
        TextView txtvLongitude = (TextView) convertView.findViewById(R.id.longitude);
        TextView txtvVaccinity = (TextView) convertView.findViewById(R.id.vacinity);


        Masjids masjid = _masjids.get(position);

        txtvMasjidName.setText(masjid.getMasjidName());
        txtvLatitude.setText(String.valueOf(masjid.getLatLong().latitude));
        txtvLongitude.setText(String.valueOf(masjid.getLatLong().longitude));
        txtvVaccinity.setText(masjid.getVacinity());


        return convertView;
    }
}
