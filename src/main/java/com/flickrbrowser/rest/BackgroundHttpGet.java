package com.flickrbrowser.rest;

import android.os.AsyncTask;
import com.flickrbrowser.util.RestClient;
import org.apache.http.client.methods.HttpGet;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackgroundHttpGet extends AsyncTask<HttpGet, Void, String> {

    public BackgroundHttpGet() {

    }

    @Override
    protected String doInBackground(HttpGet... params) {
        RestClient client = new RestClient();
        return client.doRequest(params[0]);
    }


    protected void onPostExecute(String results) {
        if (results!=null) {
            System.out.println(results);
        }
    }
}
