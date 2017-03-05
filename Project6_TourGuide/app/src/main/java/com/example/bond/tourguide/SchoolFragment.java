package com.example.bond.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bond.tourguide.R;

import java.util.ArrayList;

public class SchoolFragment extends Fragment {

    public SchoolFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list, container, false);

        // Create a list of Centers
        final ArrayList<School> schools = new ArrayList<School>();
        schools.add(new School(R.string.name_amsom, R.drawable.school_amsom, R.string.address_amsom, R.string.email_amsom));
        schools.add(new School(R.string.name_aas, R.drawable.school_aas, R.string.address_aas,R.string.email_aas));
        schools.add(new School(R.string.name_scs, R.drawable.school_scs, R.string.address_scs,R.string.email_scs));
        schools.add(new School(R.string.name_seas, R.drawable.school_seas, R.string.address_seas,R.string.email_seas));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        SchoolAdapter adapter = new SchoolAdapter(getActivity(), schools, R.color.category_family);

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
