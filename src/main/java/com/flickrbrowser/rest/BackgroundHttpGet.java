package com.flickrbrowser.rest;

import android.os.AsyncTask;
import android.widget.ListAdapter;
import com.flickrbrowser.activity.SearchActivity;
import com.flickrbrowser.util.ImageAdapter;
import com.flickrbrowser.util.RestClient;
import org.apache.http.client.methods.HttpGet;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackgroundHttpGet extends AsyncTask<HttpGet, Void, String> {
    private FlickrXmlParser parser;
    ImageAdapter adapter;
    public BackgroundHttpGet(SearchActivity searchActivity) throws ParserConfigurationException {
        parser = new FlickrXmlParser();
        adapter = searchActivity.getImageAdapter();
    }

    @Override
    protected String doInBackground(HttpGet... params) {
        RestClient client = new RestClient();
        return client.doRequest(params[0]);
    }


    protected void onPostExecute(String results) {
        if (results!=null) {
            List<PhotoResult> photos = parser.extractPhotoList(results);
            adapter.addPhotoResults(photos);
            adapter.notifyDataSetChanged();
        }
    }
}
