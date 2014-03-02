package com.flickrbrowser;

import android.app.Application;
import com.flickrbrowser.util.GzippedImageDownloader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FlickrBrowserApp extends Application {
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
    }
}
