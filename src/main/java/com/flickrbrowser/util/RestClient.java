package com.flickrbrowser.util;

import android.net.http.AndroidHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import sun.misc.IOUtils;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestClient {
    public String doRequest(HttpGet httpGet) {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("gzip");
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(httpGet);

        HttpContext localContext = new BasicHttpContext();
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();

            return getStringFromInputStream(AndroidHttpClient.getUngzippedContent(entity));
        } catch (IOException e) {
            return e.getLocalizedMessage();
        } finally {
            httpClient.close();
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


}
