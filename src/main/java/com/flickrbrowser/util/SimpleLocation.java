package com.flickrbrowser.util;

import android.location.Location;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/1/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleLocation {
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
}
