package com.example.demogetdatafromhtmlweb.fragmentUI;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demogetdatafromhtmlweb.MainActivity;
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
 * Use the {@link QuesForm1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuesForm1Fragment extends Fragment {

    Button btnTextQues1,btnTextQues2,btnTextQues3,btnTextQues4;
    ImageButton ibtnAudAns1,ibtnAudAns2,ibtnAudAns3,ibtnAudAns4;
    //word
    ArrayList<Button> listBtnChoiceQues;
    //audio
    ArrayList<ImageButton> listIbtnChoice;

    ArrayList<ContentQuestion> listContentQues;
    ArrayList<ChoiceAnswer> listChoiceQues;

    static Integer currentPos;
    static int curPosSelected;

    private Button btnNext;
    private View mView;
    TestActivity mTestActivity;
    StartUpDataFragment startUpDataFragment;
    MediaPlayer mediaPlayer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuesForm1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuesForm1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuesForm1Fragment newInstance(String param1, String param2) {
        QuesForm1Fragment fragment = new QuesForm1Fragment();
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
        mView = inflater.inflate(R.layout.fragment_ques_form1, container, false);

        AnhXa();
        CreateListImgAndAudio();

        // tạo ánh xạ frag ban gốc
        startUpDataFragment = (StartUpDataFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragmentInitData");
        mTestActivity = (TestActivity) getActivity();

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            Question questionNumber = (Question) bundleReceive.getSerializable("obj ques");
            if(questionNumber != null){
                listContentQues = questionNumber.getListContentQues();
                listChoiceQues = questionNumber.getListChoiceQues();
            }
            bundleReceive.remove("obj ques");
        }
        // trộn mảng
        Collections.shuffle(listContentQues);
        Collections.shuffle(listChoiceQues);

        for (int i=0;i<listBtnChoiceQues.size();i++) {
            listBtnChoiceQues.get(i).setText(listContentQues.get(i).getContent());
            int pos = i;
            listBtnChoiceQues.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentPos == null){
                        currentPos = pos;
                    }else {
                        if(listContentQues.get(pos).getIdContent() == listChoiceQues.get(currentPos).getIdChoice()){
                            listBtnChoiceQues.get(pos).setTextColor(Color.parseColor("#A1100F0F"));
                            listIbtnChoice.get(currentPos).setImageResource(R.drawable.ic_loud_speaker_off);
                            listIbtnChoice.get(currentPos).setEnabled(false);
                            listBtnChoiceQues.get(pos).setEnabled(false);
                            currentPos = null;
                            Toast.makeText(mView.getContext(),"đúng rồi",Toast.LENGTH_SHORT).show();
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                        }else {
                            Toast.makeText(mView.getContext(),"sai rồi",Toast.LENGTH_SHORT).show();
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                            currentPos = null;
                        }
                    }
                }
            });
        }

        // xử lý logic form matching
        for (int i=0;i<listIbtnChoice.size();i++) {
            listIbtnChoice.get(i).setImageResource(R.drawable.ic_loud_speaker);
            int pos = i;
            listIbtnChoice.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(listChoiceQues.get(pos).getChoice());
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

                    if(currentPos == null){
                        currentPos = pos;
                    }else {
                        if(listContentQues.get(currentPos).getIdContent() == listChoiceQues.get(pos).getIdChoice()){
                            listBtnChoiceQues.get(currentPos).setTextColor(Color.parseColor("#A1100F0F"));
                            listIbtnChoice.get(pos).setImageResource(R.drawable.ic_loud_speaker_off);
                            listBtnChoiceQues.get(currentPos).setEnabled(false);
                            listIbtnChoice.get(pos).setEnabled(false);
                            currentPos = null;
                            Toast.makeText(mView.getContext(),"đúng rồi",Toast.LENGTH_SHORT).show();
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                        }else {
                            Toast.makeText(mView.getContext(),"sai rồi",Toast.LENGTH_LONG).show();
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                            currentPos = null;
                        }
                    }
                }
            });
        }

        // check end of list ques
        if(startUpDataFragment.indexOfList == startUpDataFragment.mListQuestion.size()){
            btnNext.setText("End");
            btnNext.setEnabled(false);
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

    private void HoverChangeBGBtnClick(LinearLayout layout){

    }

    private void HoverChangeBGtxtClick(TextView txt){

    }

    private void CreateListImgAndAudio() {
        listBtnChoiceQues = new ArrayList<>();
        listBtnChoiceQues.add(btnTextQues1);
        listBtnChoiceQues.add(btnTextQues2);
        listBtnChoiceQues.add(btnTextQues3);
        listBtnChoiceQues.add(btnTextQues4);

        listIbtnChoice = new ArrayList<>();
        listIbtnChoice.add(ibtnAudAns1);
        listIbtnChoice.add(ibtnAudAns2);
        listIbtnChoice.add(ibtnAudAns3);
        listIbtnChoice.add(ibtnAudAns4);

    }

    private void AnhXa() {
        btnNext = mView.findViewById(R.id.btnForm1Next);
        btnTextQues1 = mView.findViewById(R.id.btn1ChoiceForm1);
        btnTextQues2 = mView.findViewById(R.id.btn2ChoiceForm1);
        btnTextQues3 = mView.findViewById(R.id.btn3ChoiceForm1);
        btnTextQues4 = mView.findViewById(R.id.btn4ChoiceForm1);
        ibtnAudAns1 = mView.findViewById(R.id.ibtnChoice1Form1);
        ibtnAudAns2 = mView.findViewById(R.id.ibtnChoice2Form1);
        ibtnAudAns3 = mView.findViewById(R.id.ibtnChoice3Form1);
        ibtnAudAns4 = mView.findViewById(R.id.ibtnChoice4Form1);
    }
}