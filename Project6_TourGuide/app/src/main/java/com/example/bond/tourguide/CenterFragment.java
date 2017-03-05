package com.example.bond.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bond.tourguide.R;

import java.util.ArrayList;


public class CenterFragment extends Fragment {

    public CenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list, container, false);

        // Create a list of Centers
        final ArrayList<Center> centers = new ArrayList<Center>();
        centers.add(new Center(R.string.center_hm, R.drawable.center_heritage, R.string.email_hm));
        centers.add(new Center(R.string.center_lf, R.drawable.center_learningfuture, R.string.email_lf));
        centers.add(new Center(R.string.center_vs, R.drawable.center_venturestudio, R.string.email_vs));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        CenterAdapter adapter = new CenterAdapter(getActivity(), centers, R.color.category_family);

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
