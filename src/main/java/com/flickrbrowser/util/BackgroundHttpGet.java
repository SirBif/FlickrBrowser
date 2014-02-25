package com.flickrbrowser.util;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import org.apache.http.client.methods.HttpGet;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackgroundHttpGet extends AsyncTask<HttpGet, Void, String> {
    private ArrayAdapter<String> adapter;

    public BackgroundHttpGet(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(HttpGet... params) {
        RestClient client = new RestClient();
        return client.doRequest(params[0]);
    }


    protected void onPostExecute(String results) {
        if (results!=null) {
            adapter.add(results);
        }
    }
}
