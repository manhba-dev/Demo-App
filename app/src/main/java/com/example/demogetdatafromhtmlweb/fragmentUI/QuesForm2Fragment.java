package com.example.demogetdatafromhtmlweb.fragmentUI;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuesForm2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuesForm2Fragment extends Fragment {

    private ImageButton ibtnAudioQues;
    private Button btnAnsA,btnAnsB,btnAnsC,btnAnsD;
    private ArrayList<Button> listBtnAns;
    private Button btnNext;

    ArrayList<ContentQuestion> listContentQues;
    ArrayList<ChoiceAnswer> listChoiceQues;
    ArrayList<Integer> listAns;


    private TextView txtQuesDetailContent;
    private View mView;

    TestActivity mTestActivity;
    StartUpDataFragment startUpDataFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuesForm2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuesForm2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuesForm2Fragment newInstance(String param1, String param2) {
        QuesForm2Fragment fragment = new QuesForm2Fragment();
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
        mView = inflater.inflate(R.layout.fragment_ques_form2, container, false);

        AnhXa();
        CreateListButtonAns();

        // get ins fra gốc
        startUpDataFragment = (StartUpDataFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragmentInitData");
        mTestActivity = (TestActivity) getActivity();

        // get bundle intent
        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            Question questionNumber = (Question) bundleReceive.getSerializable("obj ques");
            if(questionNumber != null){
                listContentQues = questionNumber.getListContentQues();
                listChoiceQues = questionNumber.getListChoiceQues();
                listAns = questionNumber.getListAnswer();
            }
            bundleReceive.remove("obj ques");
        }
        // trộn mảng
        Collections.shuffle(listContentQues);
        Collections.shuffle(listChoiceQues);

        for (int i=0;i<listContentQues.size();i++){
            int pos = i;
            ibtnAudioQues.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(listContentQues.get(pos).getContent());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                            }
                        });
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }

        for (int i=0;i<listChoiceQues.size();i++){
            listBtnAns.get(i).setText(listChoiceQues.get(i).getChoice());
            int pos = i;
            listBtnAns.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listChoiceQues.get(pos).getIdChoice() == listAns.get(0)){
                        Toast.makeText(mView.getContext(),"đúng rồi",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mView.getContext(),"sai rồi",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

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

    private void CreateListButtonAns() {
        listBtnAns = new ArrayList<>();
        listBtnAns.add(btnAnsA);
        listBtnAns.add(btnAnsB);
        listBtnAns.add(btnAnsC);
        listBtnAns.add(btnAnsD);
    }

    private void AnhXa() {
        btnNext = mView.findViewById(R.id.btnNextTwo);
        ibtnAudioQues = mView.findViewById(R.id.ibtnAudioForm2);
        btnAnsA = mView.findViewById(R.id.btnAnsAForm2);
        btnAnsB = mView.findViewById(R.id.btnAnsBForm2);
        btnAnsC = mView.findViewById(R.id.btnAnsCForm2);
        btnAnsD = mView.findViewById(R.id.btnAnsDForm2);
    }
}