package com.flickrbrowser.rest;

import android.os.AsyncTask;
import android.util.Log;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.RestClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestManager {
    private static RequestManager instance;
    private static boolean requestInProgress = false;

    private RequestManager() {

    }

    public static RequestManager getInstance() {
        if(instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    public void executeRequest(HttpGet request, IResponseListener listener) {
        if(requestInProgress) {
            Log.d(FlickrBrowserConstants.TAG, "Request already in progress. Discarding the new one");
            return;
        }
        requestInProgress = true;
        BackgroundHttpGet httpGet = new BackgroundHttpGet(listener);
        httpGet.execute(request);
    }

    private class BackgroundHttpGet extends AsyncTask<HttpGet, Void, String> {
        private IResponseListener listener;

        public BackgroundHttpGet(IResponseListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(HttpGet... params) {
            RestClient client = new RestClient();
            return client.doRequest(params[0]);

        }


        protected void onPostExecute(String response) {
            if (response!=null) {
                listener.notify(response);
            }
            requestInProgress = false;
        }
    }
}
