package com.flickrbrowser.rest;

import android.app.SearchManager;
import android.content.Intent;
import com.flickrbrowser.activity.SearchActivity;
import com.flickrbrowser.util.ImageAdapter;
import junit.framework.Assert;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class SearchResultTest {
    @org.junit.Test
    public void searchDoesntDoRequestsWhenThereAreNoMorePages() throws Exception {
        SearchActivity activity = Robolectric.buildActivity(SearchActivity.class).create().get();
        SearchResult search = new SearchResult("ferrara", new ImageAdapter(activity));
        Robolectric.addPendingHttpResponse(200, RestTest.okHttpResponse);
        search.numberOfPages = 1;
        search.currentPage = 1;
        search.loadNextPage();
        Assert.assertFalse(search.canLoadMore());
        Assert.assertEquals("No new image should be loaded", 0, activity.getImageAdapter().getCount());
    }
}
