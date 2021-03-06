package com.kleshch.excurs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FragmentBriefing extends Fragment {

    private int idNum;
    private String stringToParse, name, description, image;
    private View view;
    private TextView tvTitle;
    private DocumentView tvDescription;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final IFace activity = (IFace) getActivity();
        activity.showDialog();

        view = inflater.inflate(R.layout.fragment_excursion_briefing, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tvTitle = (TextView) view.findViewById(R.id.brief_title);
                tvDescription = (DocumentView) view.findViewById(R.id.brief_description);
                imageView = (ImageView) view.findViewById(R.id.briefing_img);

                if (getArguments() != null) {
                    idNum = getArguments().getInt("id");
                }

                getResponse();

                responseParse();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTitle.setText(name);

                        Spannable spannable = new SpannableString(description);
                        tvDescription.setText(spannable);

                        MainActivity.loader.displayImage(image, imageView);

                        Button button = (Button) view.findViewById(R.id.briefing_button);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IFace activity = (IFace) getActivity();
                                activity.beginExcursion(idNum);
                            }
                        });
                        activity.hideDialog();
                    }
                });
            }
        }).start();

        return view;
    }

    private void getResponse() {
        GetRequest request = new GetRequest();
        request.execute(getActivity().getResources().getString(R.string.url_address) +
                getActivity().getResources().getString(R.string.action_get_excurs_info) + String.valueOf(idNum));

        try {
            stringToParse = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void responseParse() {
        try {
            JSONArray array = new JSONArray(stringToParse);
            JSONObject object = array.getJSONObject(0);
            name = object.getString("name");
            description = object.getString("description");
            image = object.getString("image");
            if (!image.contains("http://")) {
                image = getResources().getString(R.string.url_domain_name) +
                        getResources().getString(R.string.images) + image;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
