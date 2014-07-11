package com.flickrbrowser.util;

public abstract class FlickrBrowserConstants {
    public static final String TAG = "FlickrSearch";
    public static final String ParcelablePhoto = "photo";
    public static final String ParcelableLocation = "location";
    public static final String ParcelableUseLocation = "use_location";
    public static final String ParcelableSearchResult = "search_result";

    public abstract class RestParameters {
        public static final String METHOD = "method";
        public static final String API_KEY = "api_key";
        public static final String FORMAT = "format";
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "lon";
        public static final String RADIUS = "radius";
        public static final String QUERY_TEXT = "text";
        public static final String EXTRAS = "extras";
        public static final String PAGE_NUMBER = "page";
        public static final String RESULTS_PER_PAGE = "per_page";
        public static final String NO_CALLBACK = "nojsoncallback";
    }
}
