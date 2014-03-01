package com.flickrbrowser.rest;

import com.flickrbrowser.TestUtil;
import junit.framework.Assert;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.maven.artifact.ant.shaded.IOUtil;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
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
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", TestUtil.getTestLocation(), 1);
        HttpResponse response = client.execute(request);
        InputStream is = response.getEntity().getContent();
        Assert.assertTrue(IOUtil.toString(is).substring(0, 100).contains("rsp stat=\"ok\""));
    }

    @Test
    public void queryWorksEvenWithoutLocation() throws URISyntaxException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", null, 1);
        HttpResponse response = client.execute(request);
        InputStream is = response.getEntity().getContent();
        Assert.assertTrue(IOUtil.toString(is).substring(0, 100).contains("rsp stat=\"ok\""));
    }

    @Test
    public void canParseResult() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.parseResponse(TestUtil.okHttpResponse).getPhotos();
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("Airone rosso", photo.getTitle());
    }

    @Test
     public void canParseDescription() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.parseResponse(TestUtil.okHttpResponse).getPhotos();
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("airone", photo.getDescription());
    }

    @Test
    public void parseDoesNotBreakWhenThereIsNoDescription() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.parseResponse(TestUtil.okHttpResponseNoDesc).getPhotos();
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
    }

    @Test
    public void badRequestGeneratesNoPhotos() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.parseResponse(TestUtil.koHttpResponse).getPhotos();
        Assert.assertEquals(0, photos.size());
    }
}
