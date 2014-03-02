package com.flickrbrowser;

import android.app.Application;
import android.provider.SearchRecentSuggestions;
import com.flickrbrowser.adapter.ImageAdapter;
import com.flickrbrowser.location.SimpleLocationListener;
import com.flickrbrowser.parcelable.SearchResult;
import com.flickrbrowser.util.GzippedImageDownloader;
import com.flickrbrowser.util.SearchHistory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FlickrBrowserApp extends Application {
    protected ImageAdapter imageAdapter = null;
    protected SearchRecentSuggestions searchSuggestions = null;
    protected SearchResult currentSearch = null;
    protected SimpleLocationListener locationListener = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create default options which will be used for every 
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
            .cacheOnDisc(true)
            .cacheInMemory(false)
            .showImageOnLoading(R.drawable.ic_launcher)
            .build();

        // Create global configuration and initialize ImageLoader with this configuration

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .imageDownloader(new GzippedImageDownloader(this))
            .discCacheFileCount(300)
            .build();
        ImageLoader.getInstance().init(config);

        imageAdapter = new ImageAdapter(this);
        searchSuggestions = new SearchRecentSuggestions(this, SearchHistory.AUTHORITY, SearchHistory.MODE);
        locationListener = new SimpleLocationListener();
    }
}
