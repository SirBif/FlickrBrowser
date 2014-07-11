package com.flickrbrowser.rest;

import android.util.Log;
import com.flickrbrowser.adapter.PhotoAdapter;
import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.parcelable.SearchResult;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.FlickrPhotoArray;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 Implements the logic used to retrieve the results of a user query, using an instance of SearchResult to keep track of the state
 @see SearchResult
 */
public class PhotoSearchManager implements IRequestListener {
    private PhotoAdapter adapter;
    private SearchResult searchResult;

    public PhotoSearchManager(PhotoAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSearchResult(SearchResult result) {
        searchResult = result;
        adapter.setPhotos(result.getPhotos());
        adapter.notifyDataSetChanged();
    }

    public SearchResult getCurrentSearchResult() {
        return searchResult;
    }

    @Override
    public void notifyEnd(FlickrPhotoArray photos) {
        searchResult.addPhotos(Arrays.asList(photos.getPhoto()));
        adapter.notifyDataSetChanged();

        if(searchResult.getNumberOfPages() == SearchResult.NO_PAGES_YET) {
            searchResult.setNumberOfPages(photos.getPages());
        }
    }

    /**
     * retrieves the first page of results from Flickr
     */
    public void loadFirstPage() {
        Log.d(FlickrBrowserConstants.TAG, "LoadFirstPage");
        loadData();
    }

    /**
     * This method checks if there are more photos available to download. If that's the case, retrieves the next page
     */
    public void loadNextPageIfPossible() {
        Log.d(FlickrBrowserConstants.TAG, "LoadNextPage " + searchResult.getCurrentPage() + "/" + searchResult.getNumberOfPages());
        if(canLoadMore()) {
            loadData();
        }
    }

    private void loadData() {
        searchResult.setCurrentPage(searchResult.getCurrentPage()+1);
        loadElements();
    }

    private void loadElements() {
        try {
            BackgroundRequestManager.getInstance().executeRequest(FlickrRequestBuilder.createRequest(searchResult), this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean canLoadMore() {
        if(searchResult.getCurrentPage() < searchResult.getNumberOfPages()) {
            return true;
        }
        return false;
    }


}
