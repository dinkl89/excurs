package com.kleshch.excurs;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentPointInfo extends Fragment {
    TextView textView, title;
    ImageView imageView;
    int finalWidth, finalHeight, pointId;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_info, container, false);

        imageView = (ImageView) view.findViewById(R.id.point_info_img);
        textView = (TextView) view.findViewById(R.id.point_info_text);
        title = (TextView) view.findViewById(R.id.point_info_title);

        if (getArguments() != null){
            pointId = getArguments().getInt("id");
        }

        PointsList pointsList = PointsList.getInstance(getActivity());
        Point point = pointsList.getPoint(pointId);
        String image = point.getImage();
        String strTitle = point.getName();
        final String info = point.getStory();

        final ViewTreeObserver observer = imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                finalWidth = imageView.getMeasuredWidth();
                finalHeight = imageView.getMeasuredHeight();
                makeSpan(info);
            }
        });

        //image. Change "..." to image
        MainActivity.loader.displayImage("https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Nicholi_Chapel_in_Novosibirsk.jpg/200px-Nicholi_Chapel_in_Novosibirsk.jpg", imageView);

        title.setText(strTitle);

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

    private void makeSpan(String text) {

        int allTestStart = 0;
        int allTextEnd = text.length() - 1;

        int lines;
        Rect bounds = new Rect();
        textView.getPaint().getTextBounds(text.substring(0,10), 0, 1, bounds);
        float fontSpacing = textView.getPaint().getFontSpacing();
        lines = (int) (finalHeight/fontSpacing);

        Log.d ("111", "Text end = " + allTextEnd + ", lines = " + lines + ",\nfontSpacing = " + fontSpacing + ", finalWidth = " + finalWidth + ", finalHeight = " + finalHeight);

        SpannableString string = new SpannableString(text);

        MyLeadingMarginSpan2 span = new MyLeadingMarginSpan2(lines, finalWidth + 10);

        string.setSpan(span, allTestStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Log.d("111", "span is: " + string.getSpanStart(span));

        textView.setText(string);
    }
}
