package com.flickrbrowser.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.flickrbrowser.R;
import com.flickrbrowser.rest.SearchResult;
import com.flickrbrowser.util.ImageAdapter;
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
    protected ImageAdapter imageAdapter = new ImageAdapter(this);
    protected SearchRecentSuggestions suggestions;
    protected SearchResult searchResult = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.main);
        suggestions = new SearchRecentSuggestions(this, SearchHistory.AUTHORITY, SearchHistory.MODE);
        getListView().setOnScrollListener(this);
        configureListView();
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
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
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
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        //leave this empty
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            int threshold = 5;
            if (listView.getLastVisiblePosition() >= listView.getCount() - 1 - threshold && searchResult != null) {
                searchResult.loadNextPage();
            }
        }
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    protected void doSearch(String query) {
        suggestions.saveRecentQuery(query, null);
        try {
            searchResult = new SearchResult(query, imageAdapter);
            imageAdapter.clearPhotos();
            searchResult.loadFirstPage();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(Search.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}