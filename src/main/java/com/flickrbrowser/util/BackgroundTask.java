package com.flickrbrowser.util;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackgroundTask extends AsyncTask<HttpGet, Void, String> {
    private ArrayAdapter<String> adapter;

    public BackgroundTask(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }


    @Override
    protected String doInBackground(HttpGet... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        try {
            HttpResponse response = httpClient.execute(params[0], localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return text;
    }


    protected void onPostExecute(String results) {
        if (results!=null) {
            adapter.add(results);
        }
    }
}
