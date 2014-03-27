package com.flickrbrowser.rest;

import com.flickrbrowser.TestUtil;
import com.flickrbrowser.parcelable.PhotoResult;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
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
    public void canQueryFlickr() throws URISyntaxException, IOException {
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", TestUtil.getTestLocation(), 1);
        GenericConnection connection = new GenericConnection(request);
        String response = IOUtils.toString(connection.getStream());
        Assert.assertTrue(response.contains("rsp stat=\"ok\""));
        connection.disconnect();
    }

    @Test
    public void queryWorksEvenWithoutLocation() throws URISyntaxException, IOException {
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", null, 1);
        GenericConnection connection = new GenericConnection(request);
        String response = IOUtils.toString(connection.getStream());
        Assert.assertTrue(response.contains("rsp stat=\"ok\""));
        connection.disconnect();
    }

    @Test(expected = MalformedURLException.class)
    public void restClientHandlesBadUrls() throws URISyntaxException, IOException {
        GenericConnection connection = new GenericConnection("somethingnotsupported");
        String response = IOUtils.toString(connection.getStream());
        connection.disconnect();
    }

    @Test
    public void canParseResult() throws ParserConfigurationException {
        List<PhotoResult> photos = FlickrXmlParser.getParser().parseResponse(TestUtil.okHttpResponse).getPhotos();
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("Airone rosso", photo.getTitle());
    }

    @Test
     public void canParseDescription() throws ParserConfigurationException {
        List<PhotoResult> photos = FlickrXmlParser.getParser().parseResponse(TestUtil.okHttpResponse).getPhotos();
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("airone", photo.getDescription());
    }

    @Test
    public void parseDoesNotBreakWhenThereIsNoDescription() throws ParserConfigurationException {
        List<PhotoResult> photos = FlickrXmlParser.getParser().parseResponse(TestUtil.okHttpResponseNoDescription).getPhotos();
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
    }

    @Test
    public void badRequestGeneratesNoPhotos() throws ParserConfigurationException {
        List<PhotoResult> photos = FlickrXmlParser.getParser().parseResponse(TestUtil.koHttpResponse).getPhotos();
        Assert.assertEquals(0, photos.size());
    }
}
