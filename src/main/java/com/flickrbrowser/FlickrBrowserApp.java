package com.flickrbrowser;

import android.app.Application;
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
            .build();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
    }
}
