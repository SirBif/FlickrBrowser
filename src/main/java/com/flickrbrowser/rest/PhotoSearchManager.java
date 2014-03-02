package com.flickrbrowser.rest;

import android.util.Log;
import com.flickrbrowser.adapter.PhotoAdapter;
import com.flickrbrowser.util.FlickrBrowserConstants;

import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/2/14
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoSearchManager implements IResponseListener {
    private PhotoAdapter adapter;
    private SearchResult searchResult;

    public PhotoSearchManager(PhotoAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSearchResult(SearchResult result) {
        searchResult = result;
        adapter.clearPhotos();
        if(result.getPhotos().size() > 0) {
            adapter.addPhotoResults(result.getPhotos());
        }
    }

    public SearchResult getCurrentSearchResult() {
        return searchResult;
    }

    @Override
    public void notify(String xmlResponse) {
        FlickrXmlParser.ParsedResponse parsedResponse = FlickrXmlParser.getParser().parseResponse(xmlResponse);
        adapter.addPhotoResults(parsedResponse.getPhotos());
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
            RequestManager.getInstance().executeRequest(FlickrRequestBuilder.createRequest(searchResult), this);
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
