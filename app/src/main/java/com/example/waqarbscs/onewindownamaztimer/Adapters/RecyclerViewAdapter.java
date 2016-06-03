package com.example.waqarbscs.onewindownamaztimer.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.waqarbscs.onewindownamaztimer.DirectionalMap;
import com.example.waqarbscs.onewindownamaztimer.Managers.AppManager;
import com.example.waqarbscs.onewindownamaztimer.MasjidDetailPage;
import com.example.waqarbscs.onewindownamaztimer.Models.Masjids;
import com.example.waqarbscs.onewindownamaztimer.R;

import java.util.List;

/**
 * Created by Imdad on 4/27/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MasjidViewHolder> implements View.OnClickListener {

    List<Masjids> _masjids;


    public RecyclerViewAdapter(List<Masjids> pMasjids) {

        this._masjids = pMasjids;
    }

    private final String Tag = "RecyclerViewAdapter";

    @Override
    public MasjidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_masjid_row, parent, false);
        MasjidViewHolder holderView = new MasjidViewHolder(view);


        return holderView;

    }

    private int currentSelectedItem = -1;

    @Override
    public void onBindViewHolder(MasjidViewHolder holder, int position) {

        holder.masjidName.setText(_masjids.get(position).getMasjidName());

        String VicinityText = _masjids.get(position).getVacinity();

        if (VicinityText.length() >= 14) {
            VicinityText = VicinityText.substring(0, 13);
            VicinityText += "...";
        }

        holder.vicinity.setText(VicinityText);
        holder.placeid.setText(_masjids.get(position).getPlaceId());

        holder.latitude.setText(String.valueOf(_masjids.get(position).getLatLong().latitude));
        holder.longitude.setText(String.valueOf(_masjids.get(position).getLatLong().longitude));

        holder.itemView.setTag(position);

        holder.imgb_Direction.setTag(position);
        holder.imgb_Time.setOnClickListener(this);

        holder.imgb_Time.setTag(position);
        holder.imgb_Direction.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        if (_masjids == null)
            return 0;

        return _masjids.size();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgb_direction:

                //well we are not calling services we are actually going to show another activity first and sed place id to it
                Intent intent = new Intent(v.getContext(), DirectionalMap.class);

                int tag = (int) v.getTag();

                Masjids tempMasjid = _masjids.get(tag);

                intent.putExtra("MasjidName", tempMasjid.getMasjidName());
                intent.putExtra("PlaceId", tempMasjid.getPlaceId());
                Log.d("LatLong", "Lat: " + tempMasjid.getLatLong().longitude);
                intent.putExtra("Longitude", String.valueOf(tempMasjid.getLatLong().longitude));
                intent.putExtra("Latitude", String.valueOf(tempMasjid.getLatLong().latitude));


                v.getContext().startActivity(intent);

                break;

            case R.id.imgb_timers:

                Intent tIntent = new Intent(v.getContext(), MasjidDetailPage.class);

                int tTag = (int) v.getTag();

                Masjids tMasjid = _masjids.get(tTag);

                tIntent.putExtra("MasjidName", tMasjid.getMasjidName());
                tIntent.putExtra("PlaceId", tMasjid.getPlaceId());


                v.getContext().startActivity(tIntent);

                break;
        }


    }

    public static class MasjidViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView masjidName;
        TextView vicinity = new TextView(AppManager.context);
        TextView longitude = new TextView(AppManager.context), latitude = new TextView(AppManager.context);
        TextView placeid = new TextView(AppManager.context);

        ImageButton imgb_Direction;
        ImageButton imgb_Time;

        MasjidViewHolder(View itemView) {

            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            masjidName = (TextView) itemView.findViewById(R.id.name);
            vicinity = (TextView) itemView.findViewById(R.id.vacinity);
            longitude = (TextView) itemView.findViewById(R.id.longitude);
            latitude = (TextView) itemView.findViewById(R.id.latitude);
            placeid = (TextView) itemView.findViewById(R.id.txtv_placeid);

            imgb_Direction = (ImageButton) itemView.findViewById(R.id.imgb_direction);
            imgb_Time = (ImageButton) itemView.findViewById(R.id.imgb_timers);

        }


    }
}
