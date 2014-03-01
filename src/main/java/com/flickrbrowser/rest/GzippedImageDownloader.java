package com.flickrbrowser.rest;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/1/14
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class GzippedImageDownloader extends BaseImageDownloader implements ImageDownloader{
    private AndroidHttpClient httpClient;

    public GzippedImageDownloader(Context context) {
        super(context);
        httpClient = AndroidHttpClient.newInstance("gzip");
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        HttpGet httpRequest = new HttpGet(imageUri);
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(httpRequest);
        HttpResponse response = httpClient.execute(httpRequest);
        HttpEntity entity = response.getEntity();
        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        return AndroidHttpClient.getUngzippedContent(bufHttpEntity);
    }
}
