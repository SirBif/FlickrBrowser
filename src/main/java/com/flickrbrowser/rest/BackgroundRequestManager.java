package com.flickrbrowser.rest;

import android.os.AsyncTask;
import android.util.Log;
import com.flickrbrowser.rest.FlickrXmlParser.ParsedResponse;
import com.flickrbrowser.util.FlickrBrowserConstants;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

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

    public void executeRequest(HttpGet request, IRequestListener listener) {
        if(requestInProgress) {
            Log.d(FlickrBrowserConstants.TAG, "Request already in progress. Discarding the new one");
            return;
        }
        requestInProgress = true;
        BackgroundHttpGet httpGet = new BackgroundHttpGet(listener);
        httpGet.execute(request);
    }

    private class BackgroundHttpGet extends AsyncTask<HttpGet, Void, ParsedResponse> {
        private IRequestListener listener;

        public BackgroundHttpGet(IRequestListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected ParsedResponse doInBackground(HttpGet... params) {
            try {
                GenericConnection connection = new GenericConnection(params[0]);
                try{
                    return FlickrXmlParser.getParser().parseResponse(connection.getStream());
                } finally {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ParsedResponse response) {
            if (response!=null) {
                listener.notifyEnd(response);
            }
            requestInProgress = false;
        }
    }
}
