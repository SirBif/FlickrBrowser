package com.flickrbrowser.rest;

import android.os.AsyncTask;
import android.util.Log;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.FlickrPhotoArray;
import com.flickrbrowser.util.FlickrResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.ZipInputStream;

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

    private class BackgroundHttpGet extends AsyncTask<HttpGet, Void, FlickrPhotoArray> {
        private IRequestListener listener;

        public BackgroundHttpGet(IRequestListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected FlickrPhotoArray doInBackground(HttpGet... params) {
            try {
                GenericConnection connection = new GenericConnection(params[0]);
                Reader reader = null;
                try{
                    reader = new InputStreamReader(new ZipInputStream(connection.getStream()));
                    JsonParser parser = new JsonParser();
                    JsonObject obj = parser.parse(reader).getAsJsonObject();
                    FlickrResponse flickrResponse = GsonHelper.getGsonInstance().fromJson(obj.get("photos"), FlickrResponse.class);
                    return flickrResponse.getPhotos();
                } finally {
                    IOUtils.closeQuietly(reader);
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(FlickrPhotoArray response) {
            if (response!=null) {
                listener.notifyEnd(response);
            }
            requestInProgress = false;
        }
    }
}
