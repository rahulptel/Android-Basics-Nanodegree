package com.example.bond.project7_booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bond on 3/3/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }


        // Find the earthquake at the given position in the list of earthquakes
        Book currentBook = getItem(position);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);

        authorTextView.setText(currentBook.getAuthor());
        titleTextView.setText(currentBook.getTitle());

        return listItemView;
    }
}
