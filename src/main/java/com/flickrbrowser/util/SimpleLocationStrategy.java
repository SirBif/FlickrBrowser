package com.flickrbrowser.util;

import android.location.*;
import android.location.Location;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/1/14
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleLocationStrategy implements LocationListener {
    private LocationManager locationManager;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final long minTime = 30 * 1000;
    private static final float minDistance = 500;
    private Location currentBestLocation;

    public SimpleLocationStrategy(LocationManager locationMgr) {
        locationManager = locationMgr;
    }

    public void startListening() {
        //to avoid this bug: http://stackoverflow.com/questions/11394825/location-manager-issue-for-ice-cream-sandwhich
        if(locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, this);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
    }

    public void stopListening() {
        locationManager.removeUpdates(this);
    }

    public SimpleLocation getLocation() {
        if(currentBestLocation == null) {
            return null;
        }
        return new SimpleLocation(currentBestLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(isBetterLocation(location)) {
            currentBestLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     */
    protected boolean isBetterLocation(Location location) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
