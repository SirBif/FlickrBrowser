package com.flickrbrowser.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import com.flickrbrowser.rest.RestTest;
import com.flickrbrowser.rest.SearchResult;
import com.flickrbrowser.util.ImageAdapter;
import junit.framework.Assert;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class SearchActivityTest {
    @org.junit.Test
    public void canBuild() throws Exception {
    Activity activity = Robolectric.buildActivity(SearchActivity.class).create().get();
    assertTrue(activity != null);
    }

    @org.junit.Test
    public void canSearch() throws Exception {
        SearchActivity activity = Robolectric.buildActivity(SearchActivity.class).create().get();
        Robolectric.addPendingHttpResponse(200, RestTest.okHttpResponse);
        activity.doSearch("ferrara");
    }

    @org.junit.Test
     public void searchIntentCanBeReceived() throws Exception {
        SearchActivity activity = Robolectric.buildActivity(SearchActivity.class).create().get();
        Robolectric.addPendingHttpResponse(200, RestTest.okHttpResponse);
        Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, "ferrara");
        activity.onNewIntent(intent);
        Assert.assertEquals(1, activity.getImageAdapter().getCount());
    }
}
