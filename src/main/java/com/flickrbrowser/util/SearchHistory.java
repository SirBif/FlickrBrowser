package com.flickrbrowser.util;

import android.content.SearchRecentSuggestionsProvider;

/**
    Simple configuration class for the default search history
 */
public class SearchHistory extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.flickrbrowser.util.SearchHistory";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchHistory() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
