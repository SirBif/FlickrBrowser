package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.widget.*;
import com.flickrbrowser.R;
import com.flickrbrowser.rest.SearchResult;
import com.flickrbrowser.util.*;
import com.flickrbrowser.rest.BackgroundHttpGet;
import com.flickrbrowser.rest.FlickrRequestBuilder;

import javax.xml.parsers.ParserConfigurationException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchActivity extends ListActivity implements AbsListView.OnScrollListener {
    private ImageAdapter imageAdapter = new ImageAdapter(this);
    private SearchRecentSuggestions suggestions;
    private SearchResult searchResult = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        suggestions = new SearchRecentSuggestions(this, SearchHistory.AUTHORITY, SearchHistory.MODE);
        getListView().setOnScrollListener(this);
        configureListView();
        handleIntent(getIntent());

        try {
            searchResult = new SearchResult("", imageAdapter);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        //leave this empty
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            int threshold = 5;
            if (listView.getLastVisiblePosition() >= listView.getCount() - 1 - threshold) {
                searchResult.loadNextPage();
            }
        }
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    protected void doSearch(String query) {
        suggestions.saveRecentQuery(query, null);
        imageAdapter.clearPhotos();
        try {
            searchResult = new SearchResult(query, imageAdapter);
            searchResult.loadNextPage();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            doSearch(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    private void configureListView() {
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(imageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(SearchActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}