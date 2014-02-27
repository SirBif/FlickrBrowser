package com.flickrbrowser.activity;

import android.app.Activity;
import com.flickrbrowser.rest.RestTest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
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
        activity.doMySearch("ferrara");
    }
}
