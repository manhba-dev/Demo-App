package com.example.demogetdatafromhtmlweb.fragmentUI;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.TestActivity;
import com.example.demogetdatafromhtmlweb.model.ChoiceAnswer;
import com.example.demogetdatafromhtmlweb.model.ContentQuestion;
import com.example.demogetdatafromhtmlweb.model.Question;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuesForm4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuesForm4Fragment extends Fragment {

    ImageButton ibtnLoudSpeaker;
    ImageButton ibtnMicRecord;
    TextView txtContentQues;
    Button btnNext;

    ArrayList<ContentQuestion> listContentQues;
    ArrayList<ChoiceAnswer> listChoiceQues;

    TextToSpeech textToSpeech;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    String contentWordVocab;

    StartUpDataFragment startUpDataFragment;
    TestActivity mTestActivity;
    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuesForm4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuesForm4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuesForm4Fragment newInstance(String param1, String param2) {
        QuesForm4Fragment fragment = new QuesForm4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_ques_form4, container, false);
        AnhXa();

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            Question questionNumber = (Question) bundleReceive.getSerializable("obj ques");
            if(questionNumber != null){
                listContentQues = questionNumber.getListContentQues();
                listChoiceQues = questionNumber.getListChoiceQues();
            }
            bundleReceive.remove("obj ques");
        }

        ShowCorrectDialog(1);

        // get ins fra gốc
        startUpDataFragment = (StartUpDataFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragmentInitData");
        //mTestActivity = (TestActivity) getActivity();

        // check null or "" trước khi gán
        txtContentQues.setText(listContentQues.get(0).getContent());

        ibtnLoudSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = txtContentQues.getText().toString();

                textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i != TextToSpeech.ERROR){
                            textToSpeech.setLanguage(Locale.ENGLISH);
                            textToSpeech.speak(content,TextToSpeech.QUEUE_FLUSH,null);
                        }
                    }
                });
            }
        });

        ibtnMicRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentWordVocab = txtContentQues.getText().toString();
                promptSpeechInput();
            }
        });

        if(startUpDataFragment.indexOfList == startUpDataFragment.mListQuestion.size()){
            btnNext.setText("End");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Integer typeQues =  Integer.valueOf(startUpDataFragment.;
                //if(typeQues != null ){
                startUpDataFragment.CheckFormQuestion(startUpDataFragment.mListQuestion.get(startUpDataFragment.indexOfList).getTypeQues());
                // }
                //Log.d("AAA",startUpDataFragment.mListQuestion.get(startUpDataFragment.indexOfList).getTypeQues()+"");
            }
        });

        return mView;
    }
    private void ShowCorrectDialog(int pos) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pass_ques_dialog_layout);
    
        Button btnNext = dialog.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // lvConver.smoothScrollToPosition(pos);

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String vocabRecord = result.get(0);
                    char[] vocabRecordCharArr = vocabRecord.toCharArray();
                    char[] vocabDBCharArr = contentWordVocab.toCharArray();

                    SpannableString spannable = new SpannableString(vocabRecord);
                    if(vocabRecord.length() > contentWordVocab.length()){
                        for (int i=0;i<contentWordVocab.length();i++){
                            if(vocabRecordCharArr[i] == vocabDBCharArr[i]){
                                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }else {
                                spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                        spannable.setSpan(new ForegroundColorSpan(Color.RED),contentWordVocab.length(),vocabRecord.length()-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        //ShowFailedDialog(spannable);
                    }else if(vocabRecord.length() < contentWordVocab.length()){
                        for (int i=0;i<vocabRecord.length();i++){
                            if(vocabRecordCharArr[i] == vocabDBCharArr[i]){
                                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }else {
                                spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                       // ShowFailedDialog(spannable);
                    }else {
                        boolean passOrFaill = true;
                        for (int i=0;i<contentWordVocab.length();i++){
                            if(vocabRecordCharArr[i] == vocabDBCharArr[i]){
                                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }else {
                                if(passOrFaill)
                                    passOrFaill = false;
                                spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

//                        if(passOrFaill)
//                            ShowCorrectDialog(vocabId);
//                        else   ShowFailedDialog(spannable);
                    }
                }
                break;
            }
        }
    }

    private void AnhXa() {
        ibtnLoudSpeaker = mView.findViewById(R.id.ibtnLoudSpeakerForm4);
        ibtnMicRecord = mView.findViewById(R.id.ibtnMicRecordAnsForm4);
        txtContentQues = mView.findViewById(R.id.txtContentQuesForm4);
        btnNext = mView.findViewById(R.id.btnNextForm4);
    }
}