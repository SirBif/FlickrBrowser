package com.flickrbrowser.rest;

import com.flickrbrowser.activity.Search;
import com.flickrbrowser.TestUtil;
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
        Search activity = Robolectric.buildActivity(TestUtil.getTestActivityClass()).create().get();
        SearchResult search = new SearchResult("ferrara", activity.getImageAdapter());
        Robolectric.addPendingHttpResponse(200, RestTest.okHttpResponse);
        search.numberOfPages = 1;
        search.currentPage = 1;
        search.loadNextPage();
        Assert.assertFalse(search.canLoadMore());
        Assert.assertEquals("No new image should be loaded", 0, activity.getImageAdapter().getCount());
    }

    @org.junit.Test
    public void searchLoadsTheExactAmountOfData() throws Exception {
        Search activity = Robolectric.buildActivity(TestUtil.getTestActivityClass()).create().get();
        SearchResult search = new SearchResult("ferrara", activity.getImageAdapter());
        Robolectric.addPendingHttpResponse(200, RestTest.okHttpResponse);
        search.loadFirstPage();
        search.loadNextPage();
        Assert.assertFalse(search.canLoadMore());
        Assert.assertEquals("No new image should be loaded", 1, activity.getImageAdapter().getCount());
    }
}
