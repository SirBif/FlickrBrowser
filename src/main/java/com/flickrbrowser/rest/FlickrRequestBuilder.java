package com.flickrbrowser.rest;

import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.Location;
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
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:45 PM
 * To change this template use File | Settings | File Templates.
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
    private static final int resultsPerPage = 40;

    public static HttpGet createRequest(String userQuery, Location location, int page) throws URISyntaxException {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.METHOD, method));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.API_KEY, apiKey));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.FORMAT, format));
        if(location != null) {
            qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.LATITUDE, location.lat.toString()));
            qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.LONGITUDE, location.lon.toString()));
            qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.RADIUS, String.valueOf(location.radius)));
        }
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.QUERY_TEXT, userQuery));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.EXTRAS, additionalFields));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.PAGE_NUMBER, String.valueOf(page)));
        qparams.add(new BasicNameValuePair(FlickrBrowserConstants.RestParameters.RESULTS_PER_PAGE, String.valueOf(resultsPerPage)));
        URI uri = URIUtils.createURI(protocol, host, port, path, URLEncodedUtils.format(qparams, "UTF-8"), null);
        HttpGet get = new HttpGet(uri);
        return get;
    }
}
