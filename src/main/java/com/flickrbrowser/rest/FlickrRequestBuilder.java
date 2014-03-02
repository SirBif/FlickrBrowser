package com.flickrbrowser.rest;

import com.flickrbrowser.parcelable.SearchResult;
import com.flickrbrowser.parcelable.SimpleLocation;
import com.flickrbrowser.util.FlickrBrowserConstants;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 Helper class used to create HttpGet requests for Flickr
 */
public abstract class FlickrRequestBuilder {
    private static final String protocol = "http";
    private static final String host = "api.flickr.com";
    private static final int port = -1;
    private static final String path = "/services/rest";
    private static final String method = "flickr.photos.search";
    private static final String apiKey = "b2990b6dd1749db8c7351de9decd46a0";
    private static final String format = "rest";
    private static final String additionalFields = "description";
    private static final int resultsPerPage = 20;
    private static final int radius = 10;

    public static HttpGet createRequest(String userQuery, SimpleLocation location, int page) throws URISyntaxException {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.METHOD, method));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.API_KEY, apiKey));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.FORMAT, format));
        if(location != null) {
            qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.LATITUDE, String.valueOf(location.getLatitude())));
            qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.LONGITUDE,String.valueOf(location.getLongitude())));
            qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.RADIUS, String.valueOf(radius)));
        }
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.QUERY_TEXT, userQuery));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.EXTRAS, additionalFields));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.PAGE_NUMBER, String.valueOf(page)));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.RESULTS_PER_PAGE, String.valueOf(resultsPerPage)));
        URI uri = URIUtils.createURI(protocol, host, port, path, URLEncodedUtils.format(qparams, "UTF-8"), null);
        HttpGet get = new HttpGet(uri);
        return get;
    }

    public static HttpGet createRequest(SearchResult result) throws URISyntaxException {
        return createRequest(result.getQuery(), result.getLocation(), result.getCurrentPage());
    }
}
