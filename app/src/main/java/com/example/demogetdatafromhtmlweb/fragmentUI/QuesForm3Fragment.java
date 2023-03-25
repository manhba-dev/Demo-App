package com.example.demogetdatafromhtmlweb.fragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.TestActivity;
import com.example.demogetdatafromhtmlweb.model.ChoiceAnswer;
import com.example.demogetdatafromhtmlweb.model.ContentQuestion;
import com.example.demogetdatafromhtmlweb.model.Question;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuesForm3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuesForm3Fragment extends Fragment {

    private GridView gridView;
    private EditText edtViewAllChoice;
    private Button btnNext;

    private int presCounter = 0;
    private int maxPresCounter = 4;
    private String[] listChoice;
    private String answer;

    ArrayList<ContentQuestion> listContentQues;
    ArrayList<ChoiceAnswer> listChoiceQues;

    StartUpDataFragment startUpDataFragment;

    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuesForm3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuesForm3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuesForm3Fragment newInstance(String param1, String param2) {
        QuesForm3Fragment fragment = new QuesForm3Fragment();
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
        mView = inflater.inflate(R.layout.fragment_ques_form3, container, false);
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

        // get ins fra gốc
        startUpDataFragment = (StartUpDataFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragmentInitData");
        //mTestActivity = (TestActivity) getActivity();

        listChoice = listChoiceQues.get(0).getChoice().split(" ");
        Log.d("AAA",listChoice.length + "");
        listChoice = shuffleArray(listChoice);

        ArrayAdapter adapter = new ArrayAdapter(mView.getContext(), android.R.layout.simple_list_item_1,listChoice);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(presCounter < listChoice.length){
                    if(presCounter == 0)
                        edtViewAllChoice.setText("");

                    edtViewAllChoice.setText(edtViewAllChoice.getText().toString() + " " + listChoice[i]);
//                        textView.startAnimation(bigsmallforth);
                    view.animate().alpha(0).setDuration(300);
                    presCounter++;
                    if(presCounter == maxPresCounter){
//                            doValidate();
                        Toast.makeText(getActivity(),"Đúng rồi",Toast.LENGTH_SHORT).show();
                    }

                }
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

        maxPresCounter = 4;

        return mView;
    }

    private String[] shuffleArray(String[] listChoice) {
        Random rnd = new Random();
        for (int i = listChoice.length-1;i>0;i--){
            int index = rnd.nextInt(i+1);
            String a = listChoice[index];
            listChoice[index] = listChoice[i];
            listChoice[i] = a;
        }
        return listChoice;
    }

    private void AnhXa() {
        gridView = mView.findViewById(R.id.listChoiceForm3);
        edtViewAllChoice = mView.findViewById(R.id.edtAnsForm3);
        btnNext = mView.findViewById(R.id.btnNextForm3);
    }
}