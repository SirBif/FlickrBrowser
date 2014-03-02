package com.flickrbrowser.rest;

import android.os.Parcel;
import android.os.Parcelable;
import com.flickrbrowser.location.SimpleLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult implements Parcelable{
    public static final int NO_PAGES_YET = 0;
    protected int currentPage;
    protected int numberOfPages;
    protected String query;
    protected SimpleLocation queryLocation;
    protected List<PhotoResult> retrievedPhotos;

    public SearchResult(String userQuery, SimpleLocation location) {
        query = userQuery;
        currentPage = NO_PAGES_YET;
        numberOfPages = NO_PAGES_YET;
        queryLocation = location;
        retrievedPhotos = new ArrayList<PhotoResult>();
    }

    public SearchResult(Parcel in) {
        query = in.readString();
        currentPage = in.readInt();
        numberOfPages = in.readInt();
        queryLocation = (SimpleLocation) in.readValue(SimpleLocation.class.getClassLoader());
        PhotoResult[] photoArray = (PhotoResult[]) in.readValue(PhotoResult.class.getClassLoader());
        retrievedPhotos = new ArrayList<PhotoResult>(Arrays.asList(photoArray));
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int number) {
        this.currentPage = number;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int number) {
        numberOfPages = number;
    }

    public String getQuery() {
        return query;
    }

    public SimpleLocation getLocation() {
        return queryLocation;
    }

    public void addPhotos(List<PhotoResult> photos) {
        retrievedPhotos.addAll(photos);
    }

    public List<PhotoResult> getPhotos() {
        return retrievedPhotos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(query);
        parcel.writeInt(currentPage);
        parcel.writeInt(numberOfPages);
        parcel.writeValue(queryLocation);
        parcel.writeValue(retrievedPhotos.toArray());
    }

    public static final Parcelable.Creator<SearchResult> CREATOR= new Parcelable.Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel source) {
            return new SearchResult(source);  //using parcelable constructor
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}
