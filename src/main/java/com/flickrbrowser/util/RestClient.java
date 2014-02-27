package com.flickrbrowser.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestClient {
    public String doRequest(HttpGet httpGet) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = EntityUtils.toString(entity);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return text;
    }
}
