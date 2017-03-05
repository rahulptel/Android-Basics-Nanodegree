package com.example.bond.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bond.tourguide.R;

import java.util.ArrayList;

/**
 * Created by bond on 2/27/2017.
 */

public class CenterAdapter extends ArrayAdapter<Center>{

    public CenterAdapter(Context context, ArrayList<Center> center, int resource) {
        super(context, 0, center);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.center_list_item, parent, false);
        }

        // Get the School object located at this position in the list
        Center currentSchool = getItem(position);

        // Find the TextView in the school_list_item.xml layout with the ID school_name.
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.school_name);
        nameTextView.setText(currentSchool.getName());

        // Find the TextView in the school_list_item.xml layout with the ID school_description.
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.school_description);
        descriptionTextView.setText(currentSchool.getDescription());

        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // If an image is available, display the provided image based on the resource ID
        imageView.setImageResource(currentSchool.getImage());
        // Make sure the view is visible
        imageView.setVisibility(View.VISIBLE);

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }

}
