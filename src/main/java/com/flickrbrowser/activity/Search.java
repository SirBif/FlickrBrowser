package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.flickrbrowser.R;
import com.flickrbrowser.adapter.ImageAdapter;
import com.flickrbrowser.location.SimpleLocationListener;
import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.parcelable.SearchResult;
import com.flickrbrowser.rest.PhotoSearchManager;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.SearchHistory;

/**
 Main activity. Handles user searches and location
 */
public class Search extends ListActivity implements AbsListView.OnScrollListener {
    protected ImageAdapter imageAdapter = null;
    protected SearchRecentSuggestions searchSuggestions = null;
    protected LinearLayout layout = null;
    protected SimpleLocationListener locationListener = null;
    protected PhotoSearchManager searchManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.main);
        imageAdapter = new ImageAdapter(this);
        locationListener = new SimpleLocationListener();
        searchSuggestions = new SearchRecentSuggestions(this, SearchHistory.AUTHORITY, SearchHistory.MODE);
        searchManager = new PhotoSearchManager(imageAdapter);

        if(savedInstanceState != null) {
            locationListener.setLocation((Location) savedInstanceState.getParcelable(FlickrBrowserConstants.ParcelableLocation));
            locationListener.setUseLocation(savedInstanceState.getBoolean(FlickrBrowserConstants.ParcelableUseLocation, false));
            searchManager.setSearchResult((SearchResult) savedInstanceState.getParcelable(FlickrBrowserConstants.ParcelableSearchResult));
            savedInstanceState.clear();
        } else {
            locationListener.setUseLocation(false); //at startup keep it "general". besides, we wouldn't have a location yet at this point
            searchManager.setSearchResult(new SearchResult("", null));
        }
        configureListView();
        layout = (LinearLayout) this.findViewById(R.id.my_layout);
        layout.requestFocus();//to hide the keyboard after switching orientation
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(FlickrBrowserConstants.ParcelableLocation, locationListener.getLocation());
        savedInstanceState.putBoolean(FlickrBrowserConstants.ParcelableUseLocation, locationListener.isUseLocation());
        savedInstanceState.putParcelable(FlickrBrowserConstants.ParcelableSearchResult, searchManager.getCurrentSearchResult());
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

    /**
     * Retrieves the search configuration for the input component
     * @param componentName
     * @return
     */
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

    /**
     * Used to switch between using the user's location in the queries or not.
     * displays a Toast with the new status
     */
    protected void switchLocationSearchMode() {
        boolean useLocationInSearch = !locationListener.isUseLocation();
        locationListener.setUseLocation(useLocationInSearch);
        /*
         I'm not turning off location updates here on purpose,
         so when the user decides he wants to include the location in his search I already have an up-to date location
        */

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
            if (listView.getLastVisiblePosition() >= listView.getCount() - 1 - threshold && searchManager.getCurrentSearchResult() != null) {
                searchManager.loadNextPageIfPossible();
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

    protected void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            doSearch(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    protected void doSearch(String query) {
        searchSuggestions.saveRecentQuery(query, null);
        layout.requestFocus();//to hide the keyboard
        searchManager.setSearchResult(new SearchResult(query, locationListener.getSimpleLocation()));
        searchManager.loadFirstPage();
    }

    protected void configureListView() {
        ListView listView = getListView();
        listView.setAdapter(imageAdapter);
        listView.setOnScrollListener(this);
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