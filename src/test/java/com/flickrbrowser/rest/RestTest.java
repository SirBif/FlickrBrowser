package com.flickrbrowser.rest;

import android.location.Location;
import android.location.LocationManager;
import com.flickrbrowser.TestUtil;
import com.flickrbrowser.util.RestClient;
import junit.framework.Assert;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestTest {
    @Test
    public void canQueryFlickr() throws URISyntaxException {
        RestClient client = new RestClient();
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", TestUtil.getTestLocation(), 1);
        String response = client.doRequest(request);
        Assert.assertTrue(response.substring(0, 100).contains("rsp stat=\"ok\""));
    }

    @Test
    public void queryWorksEvenWithoutLocation() throws URISyntaxException {
        RestClient client = new RestClient();
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", null, 1);
        String response = client.doRequest(request);
        Assert.assertTrue(response.substring(0, 100).contains("rsp stat=\"ok\""));
    }

    @Test
    public void canParseResult() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.extractPhotoList(TestUtil.okHttpResponse);
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("Airone rosso", photo.getTitle());
    }

    @Test
     public void canParseDescription() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.extractPhotoList(TestUtil.okHttpResponse);
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("airone", photo.getDescription());
    }

    @Test
    public void parseDoesNotBreakWhenThereIsNoDescription() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.extractPhotoList(TestUtil.okHttpResponseNoDesc);
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
    }

    @Test
    public void badRequestGeneratesNoPhotos() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.extractPhotoList(TestUtil.koHttpResponse);
        Assert.assertEquals(0, photos.size());
    }
}
