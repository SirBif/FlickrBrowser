package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.flickrbrowser.R;
import com.flickrbrowser.util.BackgroundHttpGet;
import com.flickrbrowser.util.FlickrRequestBuilder;
import com.flickrbrowser.util.ImageResult;
import com.flickrbrowser.util.Location;

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

        ListAdapter adapter = new ArrayAdapter<ImageResult>(
                this, // Context.
                android.R.id.list);
        setListAdapter(adapter);
        restClient = new BackgroundHttpGet((ArrayAdapter)adapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String userQuery) {
        try {
            restClient.execute(FlickrRequestBuilder.createRequest(userQuery, getCurrentLocation()));
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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