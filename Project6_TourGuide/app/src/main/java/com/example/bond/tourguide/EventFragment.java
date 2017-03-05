package com.example.bond.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bond.tourguide.R;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list, container, false);

        // Create a list of Centers
        final ArrayList<Event> events = new ArrayList<Event>();
        events.add(new Event(R.string.event_1, R.drawable.event1, "2017-3-31"));
        events.add(new Event(R.string.event_2, R.drawable.event2, "2017-2-27"));
        events.add(new Event(R.string.event_3, R.drawable.event3, "2017-2-25"));
        events.add(new Event(R.string.event_4, R.drawable.event4, "2017-2-22"));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        EventAdapter adapter = new EventAdapter(getActivity(), events, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        return rootView;
    }
}
