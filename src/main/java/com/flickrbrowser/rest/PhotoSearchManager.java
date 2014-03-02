package com.flickrbrowser.rest;

import android.util.Log;
import com.flickrbrowser.adapter.PhotoAdapter;
import com.flickrbrowser.parcelable.SearchResult;
import com.flickrbrowser.util.FlickrBrowserConstants;

import java.net.URISyntaxException;

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
        adapter.clearPhotos();
        if(result.getPhotos().size() > 0) {
            adapter.addPhotos(result.getPhotos());
        }
    }

    public SearchResult getCurrentSearchResult() {
        return searchResult;
    }

    @Override
    public void notifyEnd(String xmlResponse) {
        FlickrXmlParser.ParsedResponse parsedResponse = FlickrXmlParser.getParser().parseResponse(xmlResponse);
        adapter.addPhotos(parsedResponse.getPhotos());
        searchResult.addPhotos(parsedResponse.getPhotos());
        if(searchResult.getNumberOfPages() == SearchResult.NO_PAGES_YET) {
            searchResult.setNumberOfPages(parsedResponse.getPagesNumber());
        }
    }


    public void loadFirstPage() {
        Log.d(FlickrBrowserConstants.TAG, "LoadFirstPage");
        loadData();
    }

    public void loadNextPage() {
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
