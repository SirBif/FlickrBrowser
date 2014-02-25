package com.flickrbrowser.util;

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

    private abstract class FlickrRestParameters {
        private static final String METHOD = "method";
        private static final String API_KEY = "api_key";
        private static final String FORMAT = "format";
        private static final String LATITUDE = "lat";
        private static final String LONGITUDE = "lon";
        private static final String RADIUS = "radius";
        private static final String QUERY_TEXT = "text";
    }

    public static HttpGet createRequest(String userQuery, Location location) throws URISyntaxException {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair(FlickrRestParameters.METHOD, method));
        qparams.add(new BasicNameValuePair(FlickrRestParameters.API_KEY, apiKey));
        qparams.add(new BasicNameValuePair(FlickrRestParameters.FORMAT, format));
        qparams.add(new BasicNameValuePair(FlickrRestParameters.LATITUDE, location.lat.toString()));
        qparams.add(new BasicNameValuePair(FlickrRestParameters.LONGITUDE, location.lon.toString()));
        qparams.add(new BasicNameValuePair(FlickrRestParameters.RADIUS, String.valueOf(location.radius)));
        qparams.add(new BasicNameValuePair(FlickrRestParameters.QUERY_TEXT, userQuery));
        URI uri = URIUtils.createURI(protocol, host, port, path, URLEncodedUtils.format(qparams, "UTF-8"), null);
        HttpGet get = new HttpGet(uri);
        return get;
    }
}
