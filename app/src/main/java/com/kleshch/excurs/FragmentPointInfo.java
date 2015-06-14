package com.kleshch.excurs;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.JustifiedSpan;
import com.bluejamesbond.text.style.TextAlignment;

public class FragmentPointInfo extends Fragment {
    TextView title;
    ImageView imageView;
    int finalWidth, finalHeight, pointId;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_info, container, false);

        imageView = (ImageView) view.findViewById(R.id.point_info_img);
        title = (TextView) view.findViewById(R.id.point_info_title);
        DocumentView documentView = (DocumentView) view.findViewById(R.id.point_info_text);

        if (getArguments() != null) {
            pointId = getArguments().getInt("id");
        }

        PointsList pointsList = PointsList.getInstance(getActivity());
        Point point = pointsList.getPoint(pointId);
        String image = point.getImage();
        String strTitle = point.getName();
        String info = point.getStory();

        MainActivity.loader.displayImage(image, imageView);

        title.setText(strTitle);

        LinearLayout infoContainer = (LinearLayout) view.findViewById(R.id.infoDescriptionContainer);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(height, height);
        lpImage.setMargins(0, 10, 0, 0);

        LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpText.setMargins(5, 10, 5, 0);

        if (info.contains("$")) {
            String split[] = info.split("\\$");
            Log.d("111", "number of parts after split is " + split.length);
            Spannable spannable = new SpannableString(split[0]);
            documentView.setText(spannable);

            for (int i=1; i<split.length; i++){
                if (split[i].contains(".jpg") || split[i].contains(".png")){
                    //add imageView;
                    if (!split[i].contains("http://")) {
                        split[i] = getResources().getString(R.string.url_domain_name) +
                                getResources().getString(R.string.images) + split[i];
                    }

                    ImageView imageView1 = new ImageView(getActivity());
                    imageView1.setLayoutParams(lpImage);
                    imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    infoContainer.addView(imageView1);
                    MainActivity.loader.displayImage(split[i], imageView1);
                } else {
                    //addTextView
                    Spannable span = new SpannableString(split[i]);
                    DocumentView documentView1 = new DocumentView(getActivity());
                    documentView1.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
                    documentView1.setLayoutParams(lpText);
                    documentView1.setText(span);
                    infoContainer.addView(documentView1);
                }
            }
        } else {
            Spannable spannable = new SpannableString(info);
            spannable.setSpan(new JustifiedSpan(), 0, spannable.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            documentView.setText(spannable);
        }

        Button button = (Button) view.findViewById(R.id.point_info_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", pointId);
                FragmentQuestion question = new FragmentQuestion();
                question.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
                        .replace(R.id.fragment_container, question)
                        .addToBackStack("question")
                        .commit();
            }
        });

        return view;
    }
}
