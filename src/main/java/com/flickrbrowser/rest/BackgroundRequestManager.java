package com.flickrbrowser.rest;

import android.os.AsyncTask;
import android.util.Log;
import com.flickrbrowser.util.FlickrBrowserConstants;
import org.apache.http.client.methods.HttpGet;

/**
 Keeps track of background http requests,  enforcing a "1 at a time" constraint
 */
public class BackgroundRequestManager {
    private static BackgroundRequestManager instance;
    private static boolean requestInProgress = false;

    private BackgroundRequestManager() {

    }

    public static BackgroundRequestManager getInstance() {
        if(instance == null) {
            instance = new BackgroundRequestManager();
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
            return RestClient.execute(params[0]);
        }

        protected void onPostExecute(String response) {
            if (response!=null) {
                listener.notify(response);
            }
            requestInProgress = false;
        }
    }
}
