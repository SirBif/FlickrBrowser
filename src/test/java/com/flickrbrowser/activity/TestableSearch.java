package com.flickrbrowser.activity;

import android.app.SearchableInfo;
import android.content.ComponentName;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/28/14
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestableSearch extends Search {
    //ugly hack to avoid NPE when calling SearchManager.getSearchableInfo(componentName) from Robolectric
    @Override
    protected SearchableInfo getSearchable(ComponentName componentName) {
        return null;
    }
}