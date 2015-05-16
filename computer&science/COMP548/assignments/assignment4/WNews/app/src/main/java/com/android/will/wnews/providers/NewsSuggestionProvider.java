package com.android.will.wnews.providers;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;

public class NewsSuggestionProvider extends SearchRecentSuggestionsProvider {
    private static String AUTH = "com.android.will.wnews.providers.NewsSuggestionProvider";

    public static SearchRecentSuggestions getBridge(Context ctxt) {
        return (new SearchRecentSuggestions(ctxt, AUTH,
                DATABASE_MODE_QUERIES));
    }

    public NewsSuggestionProvider() {
        super();

        setupSuggestions(AUTH, DATABASE_MODE_QUERIES);
    }
}