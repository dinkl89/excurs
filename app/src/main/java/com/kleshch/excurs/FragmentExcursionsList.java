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
    private ArrayList<ExcursionListItem> list;
    private ListView listView;
    private View view;
    private ModelExcursionList excursionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final IFace activity = (IFace) getActivity();
        activity.showDialog();

        view = inflater.inflate(R.layout.fragment_excursions, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                excursionList = new ModelExcursionList(getActivity());

                list = excursionList.getList();

                listView = (ListView) view.findViewById(R.id.excursions_list);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ExcursionsListAdapter adapter = new ExcursionsListAdapter(list, getActivity());

                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                number = list.get(position).getItemId();
                                activity.onUserSelectValue(number);
                            }
                        });
                        activity.hideDialog();
                    }
                });
            }
        }).start();


        return view;
    }
}
