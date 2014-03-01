package com.flickrbrowser;

import com.flickrbrowser.activity.Search;
import com.flickrbrowser.activity.TestableSearch;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/28/14
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestUtil {

    public static Class<? extends Search> getTestActivityClass() {
        return TestableSearch.class;
    }
}
