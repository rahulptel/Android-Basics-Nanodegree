package com.example.bond.project7_booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by bond on 3/3/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /** Query URL */
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Book> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        List<Book> books = QueryUtils.fetchData(mUrl);
        return books;
    }
}
