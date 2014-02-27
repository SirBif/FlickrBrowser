package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.flickrbrowser.R;
import com.flickrbrowser.util.*;
import com.flickrbrowser.rest.BackgroundHttpGet;
import com.flickrbrowser.rest.FlickrRequestBuilder;

import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchActivity extends ListActivity {
    private BackgroundHttpGet restClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        restClient = new BackgroundHttpGet();
        ListView listview = (ListView) findViewById(android.R.id.list);
        listview.setAdapter(new ImageAdapter(this));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(SearchActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    protected void doMySearch(String userQuery) {
        try {
            restClient.execute(FlickrRequestBuilder.createRequest(userQuery, getCurrentLocation()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Location getCurrentLocation() {
        Location loc = new Location();
        loc.lat = 44.813019;
        loc.lon =  11.757689;
        loc.radius = 10;
        return loc;
    }
}