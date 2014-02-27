package com.flickrbrowser.rest;

import com.flickrbrowser.util.ImageAdapter;
import com.flickrbrowser.util.Location;

import javax.xml.parsers.ParserConfigurationException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {
    protected int currentPage;
    protected int numberOfPages;
    protected String query;
    private ImageAdapter imageAdapter;
    private FlickrXmlParser parser;

    public SearchResult(String query, ImageAdapter adapter) throws ParserConfigurationException {
        this.query = query;
        currentPage = 0;
        numberOfPages = 0;
        imageAdapter = adapter;
        parser = new FlickrXmlParser();

        imageAdapter.clearPhotos();
    }

    public void loadNextPage() {
        if(canLoadMore()) {
            currentPage++;
            loadElements(currentPage);
        }
    }

    private void loadElements(int currentPage) {
        try {
            BackgroundHttpGet restClient = new BackgroundHttpGet(this);
            restClient.execute(FlickrRequestBuilder.createRequest(query, getCurrentLocation(), currentPage));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void parseResponse(String xmlResponse) {
        List<PhotoResult> photos = parser.extractPhotoList(xmlResponse);
        imageAdapter.addPhotoResults(photos);
        numberOfPages = parser.extractNumberOfPages(xmlResponse);
    }

    public boolean canLoadMore() {
        if(numberOfPages > 0 && currentPage >= numberOfPages) {
            return false;
        }
        return true;
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
}
