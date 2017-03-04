package com.example.bond.newsreportapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by bond on 2/19/2017.
 */

/**
 * An NewsAdapter knows how to create a list item layout for each news
 * in the data source (a list of news objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new NewsAdapter object.
     *
     * @param context of the app
     * @param news is the list of earthquakes, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the news at the given position
     * in the list of news.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        News currentNews = getItem(position);

        // Find the TextView with view ID title
        TextView newsTitleView = (TextView) listItemView.findViewById(R.id.title);
        // Find the TextView with view ID section
        TextView newsSectionView = (TextView) listItemView.findViewById(R.id.section);

        // Set the tile of the news list item
        newsTitleView.setText(currentNews.getTitle());
        // Set the section of the news list item
        newsSectionView.setText(currentNews.getSection());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}



