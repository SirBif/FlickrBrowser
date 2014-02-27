package com.flickrbrowser.util;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchHistory extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.flickrbrowser.util.SearchHistory";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchHistory() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
