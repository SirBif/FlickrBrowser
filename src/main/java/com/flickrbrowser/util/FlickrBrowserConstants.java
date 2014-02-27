package com.flickrbrowser.util;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class FlickrBrowserConstants {

    public abstract class XmlAttributes {
        public static final String ID = "id";
        public static final String OWNER = "owner";
        public static final String SECRET = "secret";
        public static final String SERVER = "server";
        public static final String FARM = "farm";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";

        public static final String PHOTO_ATTRIBUTE_NAME = "photo";
        public static final String PAGES = "pages";

        public static final String PHOTOS_ATTRIBUTE_NAME = "photos";
    }

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
    }
}
