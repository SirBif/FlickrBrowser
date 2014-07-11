package com.flickrbrowser.rest;

import org.apache.http.client.methods.HttpGet;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 Static client that can be used to perform http requests
 */
public class GenericConnection {
    private HttpsURLConnection connection;
    private URL requestUrl;

    public GenericConnection(String url) throws MalformedURLException {
        requestUrl = new URL(url);
    }

    public GenericConnection(HttpGet request) throws MalformedURLException {
        requestUrl = request.getURI().toURL();
    }

    public InputStream getStream() throws IOException {
        disconnect();
        connection = (HttpsURLConnection) requestUrl.openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(5000);
        return new BufferedInputStream(connection.getInputStream());
    }

    public void disconnect() {
        if(connection != null) {
            connection.disconnect();
            connection = null;
        }
    }
}
