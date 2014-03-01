package com.flickrbrowser.rest;

import android.util.Log;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.ImageAdapter;
import com.flickrbrowser.util.Location;

import javax.xml.parsers.ParserConfigurationException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult implements ResponseListener{
    protected static final int NO_PAGES_YET = 0;
    protected int currentPage;
    protected int numberOfPages;
    protected String query;
    protected ImageAdapter imageAdapter;
    protected FlickrXmlParser parser;
    protected RequestManager reqManager = RequestManager.getInstance();

    public SearchResult(String userQuery, ImageAdapter adapter) throws ParserConfigurationException {
        query = userQuery;
        currentPage = NO_PAGES_YET;
        numberOfPages = NO_PAGES_YET;
        imageAdapter = adapter;
        parser = new FlickrXmlParser();

        imageAdapter.clearPhotos();
    }

    public void loadNextPage() {
        Log.d(FlickrBrowserConstants.TAG, "LoadNextPage " + currentPage + "/" + numberOfPages);
        if(canLoadMore()) {
            loadData();
        }
    }

    private void loadData() {
        currentPage++;
        loadElements();
    }

    private void loadElements() {
        try {
            reqManager.executeRequest(FlickrRequestBuilder.createRequest(query, getCurrentLocation(), currentPage), this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(String xmlResponse) {
        List<PhotoResult> photos = parser.extractPhotoList(xmlResponse);
        imageAdapter.addPhotoResults(photos);
        if(numberOfPages == NO_PAGES_YET) {
            numberOfPages = parser.extractNumberOfPages(xmlResponse);
        }
    }

    public boolean canLoadMore() {
        if(currentPage < numberOfPages) {
            return true;
        }
        return false;
    }

    private Location getCurrentLocation() {
        Location loc = new Location();
        loc.lat = 44.813019;
        loc.lon =  11.757689;
        loc.radius = 10;
        return loc;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getQuery() {
        return query;
    }

    public void loadFirstPage() {
        Log.d(FlickrBrowserConstants.TAG, "LoadFirstPage");
        loadData();
    }
}