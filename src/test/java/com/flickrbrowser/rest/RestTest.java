package com.flickrbrowser.rest;

import com.flickrbrowser.util.Location;
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

    public static final String okHttpResponse = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<rsp stat=\"ok\">\n" +
            "<photos page=\"1\" pages=\"1\" perpage=\"250\" total=\"1\">\n" +
            "    <photo id=\"1535647353\" owner=\"98545448@N00\" secret=\"e5b7537af5\" server=\"2371\" farm=\"3\" title=\"Airone rosso\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
            "</photos>\n" +
            "</rsp>";

    public static final String koHttpResponse = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<rsp stat=\"fail\">\n" +
            "\t<err code=\"112\" msg=\"Method &quot;fliclllkr.photos.search&quot; not found\" />\n" +
            "</rsp>";

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
        HttpGet request = FlickrRequestBuilder.createRequest("ferrara", getTestLocation(), 1);
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
        List<PhotoResult> photos = parser.extractPhotoList(okHttpResponse);
        Assert.assertEquals(1, photos.size());
        PhotoResult photo = photos.get(0);
        Assert.assertEquals("Airone rosso", photo.getTitle());
    }

    @Test
    public void badRequestGeneratesNoPhotos() throws ParserConfigurationException {
        FlickrXmlParser parser = new FlickrXmlParser();
        List<PhotoResult> photos = parser.extractPhotoList(koHttpResponse);
        Assert.assertEquals(0, photos.size());
    }
}
