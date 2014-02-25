package com.flickrbrowser.util;

import junit.framework.Assert;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestTest {

    private Location getTestLocation() {
        Location loc = new Location();
        loc.lat = 44.813019;
        loc.lon =  11.757689;
        loc.radius = 10;
        return loc;
    }

    @Test
    public void canQueryFlickr() throws URISyntaxException {
        RestClient client = new RestClient();
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", getTestLocation());
        String response = client.doRequest(request);
        Assert.assertTrue(response.substring(0, 100).contains("rsp stat=\"ok\""));
    }
}
