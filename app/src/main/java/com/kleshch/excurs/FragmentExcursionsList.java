package com.kleshch.excurs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentExcursionsList extends Fragment {
    private int number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excursions, container, false);

        ModelExcursionList excursionList = new ModelExcursionList(getActivity());

        final ArrayList<ExcursionListItem> list = excursionList.getList();

        final ListView listView = (ListView) view.findViewById(R.id.excursions_list);
        ExcursionsListAdapter adapter = new ExcursionsListAdapter(list, getActivity());

        listView.setAdapter(adapter);

        final IFace activity = (IFace) getActivity();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                number = list.get(position).getItemId();
                activity.onUserSelectValue(number);
            }
        });

        return view;
    }
}
