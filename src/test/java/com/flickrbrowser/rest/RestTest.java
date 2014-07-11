package com.flickrbrowser.rest;

import com.flickrbrowser.TestUtil;
import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.util.FlickrPhotoArray;
import com.flickrbrowser.util.FlickrResponse;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestTest {
    @Test
    public void theRequestFormatIsUnderstoodByFlickr() throws URISyntaxException, IOException {
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", TestUtil.getTestLocation(), 1);
        GenericConnection connection = new GenericConnection(request);
        String response = IOUtils.toString(connection.getStream());
        Assert.assertTrue(response.contains("\"stat\":\"ok\""));
        connection.disconnect();
    }

    @Test
    public void theRequestWorksEvenWithoutLocation() throws URISyntaxException, IOException {
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", null, 1);
        GenericConnection connection = new GenericConnection(request);
        String response = IOUtils.toString(connection.getStream());
        Assert.assertTrue(response.contains("\"stat\":\"ok\""));
        connection.disconnect();
    }

    @Test(expected = MalformedURLException.class)
    public void genericConnectionHandlesBadUrls() throws URISyntaxException, IOException {
        GenericConnection connection = new GenericConnection("somethingnotsupported");
        String response = IOUtils.toString(connection.getStream());
        connection.disconnect();
    }

    @Test
    public void canParseJson() throws ParserConfigurationException {
        PhotoResult[] photos = GsonHelper.fromJson(TestUtil.okHttpResponse, FlickrResponse.class).getPhotos().getPhoto();
        Assert.assertEquals(1, photos.length);
        PhotoResult photo = photos[0];
        Assert.assertEquals("serbatoio dell' acqua - tank of water", photo.getTitle());
    }

    @Test
     public void canParseJsonWithDescription() throws ParserConfigurationException {
        PhotoResult[] photos = GsonHelper.fromJson(TestUtil.okHttpResponse, FlickrResponse.class).getPhotos().getPhoto();
        Assert.assertEquals(1, photos.length);
        PhotoResult photo = photos[0];
        Assert.assertEquals("serbatoio", photo.getDescriptionString());
    }

    @Test
    public void canParseJsonWithNoDescription() throws ParserConfigurationException {
        PhotoResult[] photos = GsonHelper.fromJson(TestUtil.okHttpResponseNoDescription, FlickrResponse.class).getPhotos().getPhoto();
        Assert.assertEquals(1, photos.length);
        PhotoResult photo = photos[0];
    }

    @Test
    public void badRequestGeneratesNoPhotos() throws ParserConfigurationException {
        FlickrPhotoArray photos = GsonHelper.fromJson(TestUtil.koHttpResponse, FlickrResponse.class).getPhotos();
        Assert.assertNull(photos);
    }

    @Test
    public void canGetPhotosFromFlickr() throws ParserConfigurationException, URISyntaxException, IOException {
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", null, 1);
        FlickrPhotoArray photos = BackgroundRequestManager.getPhotos(request);
        Assert.assertNotNull(photos);
        Assert.assertNotSame(0, photos.getTotal());
    }
}
