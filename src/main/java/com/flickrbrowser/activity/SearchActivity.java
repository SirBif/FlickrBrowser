package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.flickrbrowser.R;
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
    private BackgroundHttpGet restClient;
    private int currentPage = 0;
    private String currentQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            restClient = new BackgroundHttpGet();
            getListView().setOnScrollListener(this);
            configureListClicks();
            handleIntent();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
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
                currentPage++;
                //load more list items:
                loadElements(currentPage);
            }
        }
    }

    private void loadElements(int currentPage) {
        try {
            restClient.execute(FlickrRequestBuilder.createRequest(currentQuery, getCurrentLocation(), currentPage));
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected void doSearch(String query) {
        currentQuery = query;
        currentPage = 1;
        loadElements(currentPage);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            doSearch(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    private void configureListClicks() {
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new ImageAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(SearchActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Location getCurrentLocation() {
        Location loc = new Location();
        loc.lat = 44.813019;
        loc.lon =  11.757689;
        loc.radius = 10;
        return loc;
    }
}