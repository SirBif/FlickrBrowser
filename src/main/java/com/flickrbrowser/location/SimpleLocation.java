package com.flickrbrowser.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/1/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleLocation implements Parcelable{
    private double latitude;
    private double longitude;

    public SimpleLocation() {

    }

    public SimpleLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "["+latitude+", "+longitude+"]";
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(latitude);
        parcel.writeValue(longitude);
    }

    public SimpleLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Parcelable.Creator<SimpleLocation> CREATOR= new Parcelable.Creator<SimpleLocation>() {
        @Override
        public SimpleLocation createFromParcel(Parcel source) {
            return new SimpleLocation(source);  //using parcelable constructor
        }

        @Override
        public SimpleLocation[] newArray(int size) {
            return new SimpleLocation[size];
        }
    };
}
