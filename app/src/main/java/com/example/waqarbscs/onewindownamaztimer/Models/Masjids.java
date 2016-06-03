package com.example.waqarbscs.onewindownamaztimer.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Imdad on 4/27/2016.
 */
public class Masjids implements Parcelable {

    private int id;
    private String _masjidName;
    private LatLng _latlong;
    private String _vacitniy;
    private String _fajarTime;
    private String _doharTime;
    private String _asarTime;
    private String _magribTime;
    private String _eshaTime;
    private String _placeid;
    private String _jummaTime;

    public Masjids() {

        id = 0;
        _masjidName = "NA";
        _latlong = new LatLng(0, 0);
        _vacitniy = "NA";
        _fajarTime = "NA";
        _doharTime = "NA";
        _asarTime = "NA";
        _magribTime = "NA";
        _eshaTime = "NA";
        _placeid = "NA";
        _jummaTime = "NA";

    }

    public void setInitialValues() {
        id = 0;
        _masjidName = "NA";
        _latlong = new LatLng(0, 0);
        _vacitniy = "NA";
        _fajarTime = "NA";
        _doharTime = "NA";
        _asarTime = "NA";
        _magribTime = "NA";
        _eshaTime = "NA";
        _placeid = "NA";
        _jummaTime = "NA";
    }

    protected Masjids(Parcel in) {
        id = in.readInt();
        _masjidName = in.readString();
        _latlong = in.readParcelable(LatLng.class.getClassLoader());
        _vacitniy = in.readString();
        _fajarTime = in.readString();
        _doharTime = in.readString();
        _asarTime = in.readString();
        _magribTime = in.readString();
        _eshaTime = in.readString();
        _placeid = in.readString();

        _jummaTime = in.readString();
    }

    public static final Creator<Masjids> CREATOR = new Creator<Masjids>() {
        @Override
        public Masjids createFromParcel(Parcel in) {
            return new Masjids(in);
        }

        @Override
        public Masjids[] newArray(int size) {
            return new Masjids[size];
        }
    };

    public String getPlaceId() {
        return _placeid;
    }

    public void setPlaceId(String pPlaceId) {
        _placeid = pPlaceId;
    }


    public void setMasjidName(String pMasjidName) {
        _masjidName = pMasjidName;
    }

    public void setLatLong(LatLng pLatLong) {
        _latlong = pLatLong;
    }

    public void setVacinity(String pVacinity) {
        _vacitniy = pVacinity;
    }

    public void setFajarTime(String pFajarTime) {
        _fajarTime = pFajarTime;
    }

    public void setDoharTime(String pDoharTime) {
        _doharTime = pDoharTime;
    }

    public void setAsarTime(String pAsarTime) {
        _asarTime = pAsarTime;
    }

    public void setMagribTime(String pMagribTime) {
        _magribTime = pMagribTime;
    }

    public void setEshaTime(String pEshaTime) {
        _eshaTime = pEshaTime;
    }

    public void setJummaTime(String pJummaTime) {
        _jummaTime = pJummaTime;
    }

    public String getJummaTime() {
        return _jummaTime;
    }

    public String getMasjidName() {
        return _masjidName;
    }

    public LatLng getLatLong() {
        return _latlong;
    }

    public String getVacinity() {
        return _vacitniy;
    }

    public String getFajarTime() {
        return _fajarTime;
    }

    public String getDoharTime() {
        return _doharTime;
    }

    public String getAsarTime() {
        return _asarTime;
    }

    public String getMagribTime() {
        return _magribTime;
    }

    public String getEshaTime() {
        return _eshaTime;
    }

    public int getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(_masjidName);
        dest.writeParcelable(_latlong, flags);

        dest.writeString(_vacitniy);
        dest.writeString(_fajarTime);
        dest.writeString(_doharTime);
        dest.writeString(_asarTime);
        dest.writeString(_magribTime);
        dest.writeString(_eshaTime);
        dest.writeString(_placeid);
        dest.writeString(_jummaTime);

    }
}
