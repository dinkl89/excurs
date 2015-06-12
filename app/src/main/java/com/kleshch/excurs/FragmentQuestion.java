package com.kleshch.excurs;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentQuestion extends Fragment {

    private String question, strAnswer;
    private int pointId;
    private EditText edAnswer;
    private IFace face;
    private PointsList pointsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        pointsList = PointsList.getInstance(getActivity());
        face = (IFace)getActivity();

        if (getArguments() != null) {
            pointId = getArguments().getInt("id");
            Point point = pointsList.getPoint(pointId);
            question = point.getQuestion();
            strAnswer = point.getAnswer();
            Log.d ("111", "Answer is: " + strAnswer);
        }

        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        edAnswer = (EditText) view.findViewById(R.id.edAnswer);
        edAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE||
                        event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edAnswer.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    checkAnswer();
                    return true;
                }
                    return false;
            }
        });

        tvQuestion.setText(question);

        Button button = (Button) view.findViewById(R.id.question_check);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        return view;
    }

    private void checkAnswer(){
        String temp = edAnswer.getText().toString();
        Log.d("111", "Answer is \"" + strAnswer + "\", you entered " + temp);

        if(convertString(temp).equals(convertString(strAnswer))){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.answer_check))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.right_answer))
                    .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (pointId < pointsList.getListLength()-1) {
                                face.nextPoint(pointId + 1);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.excursion_end), Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                face.getExcursionsList();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private String convertString(String input){
       return input.toLowerCase().replaceAll(" ", "");
    }
}
