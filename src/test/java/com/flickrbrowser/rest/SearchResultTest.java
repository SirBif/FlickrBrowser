package com.flickrbrowser.rest;

import com.flickrbrowser.TestUtil;
import com.flickrbrowser.adapter.PhotoAdapter;
import com.flickrbrowser.parcelable.SearchResult;
import junit.framework.Assert;
import org.junit.runner.RunWith;
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
        PhotoAdapter listener = new PhotoAdapter();
        SearchResult search = new SearchResult("ferrara", TestUtil.getTestLocation());
        PhotoSearchManager searchManager = new PhotoSearchManager(listener);
        search.setCurrentPage(1);
        search.setNumberOfPages(1);
        searchManager.setSearchResult(search);
        searchManager.loadNextPage();
        Assert.assertFalse(searchManager.canLoadMore());
        Assert.assertEquals("No new image should be loaded", 0, listener.getCount());
    }
}
