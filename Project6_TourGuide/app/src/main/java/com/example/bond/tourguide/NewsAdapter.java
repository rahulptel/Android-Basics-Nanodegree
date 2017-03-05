package com.example.bond.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bond.tourguide.R;

import java.util.ArrayList;

/**
 * Created by bond on 2/27/2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, ArrayList<News> news, int resource) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Get the News object located at this position in the list
        News currentNews = getItem(position);

        // Find the TextView in the news_list_item.xml layout with the ID news_title.
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.news_title);
        nameTextView.setText(currentNews.getTitle());

        // Find the TextView in the school_list_item.xml layout with the ID school_description.
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.news_date);
        descriptionTextView.setText(currentNews.getDate());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
