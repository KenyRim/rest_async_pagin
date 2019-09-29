package com.kenyrim.appdevtest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {
    private String id,name,country,lat,lon;

    protected Model(Parcel in) {
        id = in.readString();
        name = in.readString();
        country = in.readString();
        lat = in.readString();
        lon = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel,int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(country);
        parcel.writeString(lat);
        parcel.writeString(lon);
    }
}
