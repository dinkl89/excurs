package com.kleshch.excurs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExcursionsListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ExcursionListItem> list;

    public ExcursionsListAdapter(ArrayList<ExcursionListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.excursions_list_item, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(R.id.list_item_text);
        textView.setText(list.get(position).getName());

        return view;
    }
}
