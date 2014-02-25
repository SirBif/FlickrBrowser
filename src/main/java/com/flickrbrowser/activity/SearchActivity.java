package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.flickrbrowser.R;
import com.flickrbrowser.util.BackgroundTask;
import com.flickrbrowser.util.ImageResult;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpParams;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchActivity extends ListActivity {
    private BackgroundTask restClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListAdapter adapter = new ArrayAdapter<ImageResult>(
                this, // Context.
                android.R.id.list);
        setListAdapter(adapter);
        restClient = new BackgroundTask((ArrayAdapter)adapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String userQuery) {
        restClient.execute(composeQueryString(userQuery));
    }

    private HttpGet composeQueryString(String userQuery) {
        String base = "http://api.flickr.com/services/rest/";
        String method = "flickr.photos.search";
        String apiKey = "67cddab5d85093d72d9b4ac37358838b";
        String apiSig = "b985911f2c4488d8792b9ee67e32d93f";
        Double lat = 44.813019;
        Double lon = 11.757689;
        int radius = 10;
        String format = "rest";
        HttpGet get = new HttpGet(base);
        HttpParams params = get.getParams();
        params.setParameter("method", method);
        params.setParameter("api_key", apiKey);
        params.setParameter("api_sig", apiSig);
        params.setParameter("format", format);
        params.setDoubleParameter("lat", lat);
        params.setDoubleParameter("lon", lon);
        params.setIntParameter("radius", radius);
        params.setParameter("text", userQuery);
        get.setParams(params);
        return get;
    }
}