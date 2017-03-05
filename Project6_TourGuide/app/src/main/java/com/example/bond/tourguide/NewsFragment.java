package com.example.bond.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bond.tourguide.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list, container, false);

        // Create a list of Centers
        final ArrayList<News> news = new ArrayList<News>();
        news.add(new News("Blood Donation Camp", "2017-2-22"));
        news.add(new News("The Economic Times Interviews University Leadership", "2017-2-21"));
        news.add(new News("DBLS Students Win Best Poster Award", "2017-2-21"));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        NewsAdapter adapter = new NewsAdapter(getActivity(), news, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link CenterAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Center} in the list.
        listView.setAdapter(adapter);

        return rootView;

    }
}
