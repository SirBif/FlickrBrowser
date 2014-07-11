package com.flickrbrowser.rest;

import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.util.FlickrPhotoArray;
import com.flickrbrowser.util.FlickrResponse;
import com.google.gson.Gson;
import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by bif on 14/04/14.
 */
public class PhotoResultGsonTest {
    @Test
    public void testParseSingleJsonResult_noDescription() {
        PhotoResult photo = GsonHelper.fromJson(photoJson_noDesc, PhotoResult.class);
        Assert.assertEquals("13856542784", photo.getId());
        Assert.assertEquals("28776944@N06", photo.getOwner());
        Assert.assertEquals("680dd406f4", photo.getSecret());
        Assert.assertEquals("7040", photo.getServer());
        Assert.assertEquals("8", photo.getFarm());
        Assert.assertEquals("i vicoli di Ferrara (3)", photo.getTitle());
    }

    @Test
    public void testParseSingleJsonResult_withDescription() {
        PhotoResult photo = GsonHelper.fromJson(photoJson_withDesc, PhotoResult.class);
        Assert.assertEquals("test", photo.getDescriptionString());
    }

    @Test
    public void testStream() {
        InputStream is = new ByteArrayInputStream(photoJson_noDesc.getBytes());
        PhotoResult photo = GsonHelper.fromJson(is, PhotoResult.class);
        Assert.assertEquals("13856542784", photo.getId());
    }

    @Test
    public void testParseMultiplePhotos() {
        FlickrResponse flickrResponse = GsonHelper.fromJson(wholeJson, FlickrResponse.class);
        FlickrPhotoArray photos = flickrResponse.getPhotos();
        Assert.assertEquals(1, photos.getPage());
        Assert.assertEquals(7423, photos.getPages());
        Assert.assertEquals(20, photos.getPerpage());
        Assert.assertEquals(148443, photos.getTotal());
        Assert.assertEquals(5, photos.getPhoto().length);
    }

    private static String photoJson_noDesc = "{ \"id\": \"13856542784\", \"owner\": \"28776944@N06\", \"secret\": \"680dd406f4\", \"server\": \"7040\", \"farm\": 8, \"title\": \"i vicoli di Ferrara (3)\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 }";
    private static String photoJson_withDesc = "{ \"id\": \"13856426184\", \"owner\": \"28776944@N06\", \"secret\": \"b85ed500d3\", \"server\": \"2849\", \"farm\": 3, \"title\": \"i vicoli di Ferrara (2)\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0, \n" +
            "        \"description\": { \"_content\": \"test\" } }";

    private static String wholeJson = "{ \"photos\": { \"page\": 1, \"pages\": \"7423\", \"perpage\": 20, \"total\": \"148443\", \n" +
            "    \"photo\": [\n" +
            "      { \"id\": \"13856542784\", \"owner\": \"28776944@N06\", \"secret\": \"680dd406f4\", \"server\": \"7040\", \"farm\": 8, \"title\": \"i vicoli di Ferrara (3)\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0, \n" +
            "        \"description\": { \"_content\": \"aaaa\" } },\n" +
            "      { \"id\": \"13856426184\", \"owner\": \"28776944@N06\", \"secret\": \"b85ed500d3\", \"server\": \"2849\", \"farm\": 3, \"title\": \"i vicoli di Ferrara (2)\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0, \n" +
            "        \"description\": { \"_content\": \"\" } },\n" +
            "      { \"id\": \"13855801873\", \"owner\": \"28776944@N06\", \"secret\": \"78592c344a\", \"server\": \"3816\", \"farm\": 4, \"title\": \"i vicoli di Ferrara\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0, \n" +
            "        \"description\": { \"_content\": \"\" } },\n" +
            "      { \"id\": \"13855364445\", \"owner\": \"112919528@N03\", \"secret\": \"2e50b7a76f\", \"server\": \"2872\", \"farm\": 3, \"title\": \"Eccoci puntuali anche questa sera con il 3 indizio: Tra il Sacro e il Profano dovrai Guardare se il terzo uovo ti vuoi accaparrare, in un muro è celato di quel luogo Sconsacrato!!! Buona caccia!!! #igersferrara #instanuovafe #comunediferrara #ferrara #cat\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0, \n" +
            "        \"description\": { \"_content\": \"via Instagram <a href=\\\"http:\\/\\/ift.tt\\/1kTrIMc\\\" rel=\\\"nofollow\\\">ift.tt\\/1kTrIMc<\\/a>\" } },\n" +
            "      { \"id\": \"13854407503\", \"owner\": \"54804589@N05\", \"secret\": \"4268d3d2ca\", \"server\": \"7353\", \"farm\": 8, \"title\": \"ferrara\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0, \n" +
            "        \"description\": { \"_content\": \"\" } }\n" +
            "    ] }, \"stat\": \"ok\" }";
}
