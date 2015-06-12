package com.kleshch.excurs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BriefingListAdapter extends BaseAdapter {

    String[] titles, descriptions;
    String title, description, image;
    Context context;
    //TextView tvTitle, tvDescription;

    public BriefingListAdapter(String[] titles, String[] descriptions, Context context){
        this.titles = titles;
        this.descriptions = descriptions;
        this.context = context;
    }

    public BriefingListAdapter(String name, String description, String image, Context applicationContext) {
        this.title = name;
        this.description = description;
        this.image = image;
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            view = inflater.inflate(R.layout.briefing_item, parent, false);
        } else {
            view = convertView;
        }

        TextView tvTitle = (TextView) view.findViewById(R.id.brief_title);
        TextView tvDescription = (TextView) view.findViewById(R.id.brief_description);

        Log.d("111", "Try to place string: " + titles[position]);

        tvTitle.setText("• " + title);
        tvDescription.setText(description);

        return view;
    }
}
