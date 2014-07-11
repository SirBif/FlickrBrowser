package com.flickrbrowser.parcelable;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 Simplified location (compare to android.location.Location). Easier to mock
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

    public SimpleLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof SimpleLocation == false) return false;

        SimpleLocation sl = (SimpleLocation) o;
        if(Double.compare(latitude, sl.latitude) != 0) return false;
        if(Double.compare(longitude, sl.longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 19;
        long doubleFieldBits;
        doubleFieldBits = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (doubleFieldBits ^ (doubleFieldBits >>> 32));
        doubleFieldBits = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (doubleFieldBits ^ (doubleFieldBits >>> 32));
        return result;
    }
}
