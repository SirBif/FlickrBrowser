package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.flickrbrowser.R;
import com.flickrbrowser.location.SimpleLocationListener;
import com.flickrbrowser.rest.PhotoResult;
import com.flickrbrowser.rest.SearchResult;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.adapter.ImageAdapter;
import com.flickrbrowser.util.SearchHistory;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Search extends ListActivity implements AbsListView.OnScrollListener {
    protected ImageAdapter imageAdapter;
    protected SearchRecentSuggestions searchSuggestions;
    protected SearchResult currentSearch = null;
    protected LinearLayout layout;
    protected SimpleLocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.main);
        imageAdapter = new ImageAdapter(this);
        searchSuggestions = new SearchRecentSuggestions(this, SearchHistory.AUTHORITY, SearchHistory.MODE);
        locationListener = new SimpleLocationListener();
        locationListener.setUseLocation(false); //at startup keep it "general". besides, we wouldn't have a location yet at this point
        getListView().setOnScrollListener(this);
        configureListView();
        layout = (LinearLayout) this.findViewById(R.id.my_layout);
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        // Add SearchWidget.
        SearchView searchView = (SearchView) menu.findItem( R.id.action_search ).getActionView();
        searchView.setSearchableInfo(getSearchable(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    protected SearchableInfo getSearchable(ComponentName componentName) {
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        //the following line throws NPE when executed from Robolectric
        SearchableInfo searchable = searchManager.getSearchableInfo(componentName);
        if (searchable == null || searchable.getSuggestAuthority() == null) {
            throw new RuntimeException("Component is not searchable: "
                    + componentName.flattenToShortString());
        }
        return searchable;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_use_location:
                switchLocationSearchMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void switchLocationSearchMode() {
        boolean useLocationInSearch = !locationListener.isUseLocation();
        locationListener.setUseLocation(useLocationInSearch);

        CharSequence text = useLocationInSearch ? getString(R.string.locationWillBeUsed) : getString(R.string.locationWillNotBeUsed);
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
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
            if (listView.getLastVisiblePosition() >= listView.getCount() - 1 - threshold && currentSearch != null) {
                currentSearch.loadNextPage();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final long minTime = 30 * 1000;
        final float minDistance = 500;
        //to avoid this bug: http://stackoverflow.com/questions/11394825/location-manager-issue-for-ice-cream-sandwhich
        if(locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(locationListener);
    }

    //needed for testing
    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    protected void doSearch(String query) {
        searchSuggestions.saveRecentQuery(query, null);
        layout.requestFocus();
        try {
            currentSearch = new SearchResult(query, locationListener.getLocation(), imageAdapter);
            imageAdapter.clearPhotos();
            currentSearch.loadFirstPage();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            doSearch(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    protected void configureListView() {
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(imageAdapter);
        final Context ctx = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PhotoResult photo = (PhotoResult) imageAdapter.getItem(position);
                Intent newIntent = new Intent("display");
                newIntent.putExtra(FlickrBrowserConstants.ParcelablePhoto, photo);
                newIntent.setClass(ctx, Display.class);
                startActivity(newIntent);
            }
        });
    }
}