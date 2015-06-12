package com.kleshch.excurs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentExcursionsList extends Fragment {
    int number;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.excursions_list){
            menu.add(getResources().getStringArray(R.array.dialog_items)[0]);
            menu.add(getResources().getStringArray(R.array.dialog_items)[1]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        IFace activity = (IFace) getActivity();
        activity.onUserSelectValue(number, item.getTitle().toString());
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excursions, container, false);

        ModelExcursionList excursionList = new ModelExcursionList(getActivity());

        final ArrayList<ExcursionListItem> list = excursionList.getList();

        final ListView listView = (ListView) view.findViewById(R.id.excursions_list);
        ExcursionsListAdapter adapter = new ExcursionsListAdapter(list, getActivity());

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                number = list.get(position).getItemId();
                getActivity().openContextMenu(listView);
            }
        });

        return view;
    }
}
