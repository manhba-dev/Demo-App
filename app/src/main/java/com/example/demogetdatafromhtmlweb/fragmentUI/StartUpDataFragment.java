package com.example.demogetdatafromhtmlweb.fragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demogetdatafromhtmlweb.MainActivity;
import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.TestActivity;
import com.example.demogetdatafromhtmlweb.model.ChoiceAnswer;
import com.example.demogetdatafromhtmlweb.model.ContentQuestion;
import com.example.demogetdatafromhtmlweb.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartUpDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartUpDataFragment extends Fragment {

    private RecyclerView rcvQues;
    private View mView;
    private TestActivity mTestActivity;
    public static List<Question> mListQuestion;
    public static int indexOfList = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StartUpDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartUpDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartUpDataFragment newInstance(String param1, String param2) {
        StartUpDataFragment fragment = new StartUpDataFragment();
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
        mView = inflater.inflate(R.layout.fragment_start_up_data, container, false);
        mTestActivity = (TestActivity) getActivity();

        mListQuestion = createListQuestion();

        FragmentTransaction fragmentTransaction = mTestActivity.getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        if(mListQuestion.get(indexOfList).getTypeQues() == 1){
            QuesForm1Fragment typeQuesOneFragment = new QuesForm1Fragment();

            bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

            typeQuesOneFragment.setArguments(bundle);
            indexOfList++;
            fragmentTransaction.add(R.id.content_frame,typeQuesOneFragment,"fragQuesForm1");

        }else if(mListQuestion.get(indexOfList).getTypeQues() == 2){
            QuesForm2Fragment typeQuesTwoFragment = new QuesForm2Fragment();
            bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

            typeQuesTwoFragment.setArguments(bundle);
            indexOfList++;
            fragmentTransaction.add(R.id.content_frame,typeQuesTwoFragment,"fragQuesForm2");
        }else if(mListQuestion.get(indexOfList).getTypeQues() == 3){
            QuesForm3Fragment typeQuesThreeFragment = new QuesForm3Fragment();
            bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

            typeQuesThreeFragment.setArguments(bundle);
            indexOfList++;
            fragmentTransaction.add(R.id.content_frame,typeQuesThreeFragment,"fragQuesForm3");
        }
        fragmentTransaction.commit();

        return mView;
    }

    public void CheckFormQuestion(int id){
       //int id = mListQuestion.get(indexOfList).getTypeQues();
        FragmentTransaction fragmentTransaction = mTestActivity.getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        switch (id) {
            case 1:
                QuesForm1Fragment typeQuesOneFragment = new QuesForm1Fragment();
                bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

                typeQuesOneFragment.setArguments(bundle);
                indexOfList++;
                fragmentTransaction.add(R.id.content_frame,typeQuesOneFragment,"fragQuesForm1");
                deleteFragmentAfterShow(fragmentTransaction);
                break;
            case 2:
                QuesForm2Fragment typeQuesTwoFragment = new QuesForm2Fragment();
                bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

                typeQuesTwoFragment.setArguments(bundle);
                indexOfList++;
                fragmentTransaction.add(R.id.content_frame,typeQuesTwoFragment,"fragQuesForm2");
                deleteFragmentAfterShow(fragmentTransaction);
                break;
            case 3:
                QuesForm3Fragment typeQuesThreeFragment = new QuesForm3Fragment();
                bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

                typeQuesThreeFragment.setArguments(bundle);
                indexOfList++;
                fragmentTransaction.add(R.id.content_frame,typeQuesThreeFragment,"fragQuesForm3");
                deleteFragmentAfterShow(fragmentTransaction);
                break;
            case 4:
                QuesForm4Fragment typeQuesFourFragment = new QuesForm4Fragment();
                bundle.putSerializable("obj ques", mListQuestion.get(indexOfList));

                typeQuesFourFragment.setArguments(bundle);
                indexOfList++;
                fragmentTransaction.add(R.id.content_frame,typeQuesFourFragment,"fragQuesForm4");
                deleteFragmentAfterShow(fragmentTransaction);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    public void deleteFragmentAfterShow(FragmentTransaction fragmentTransaction){
        QuesForm1Fragment quesForm1Fragment = (QuesForm1Fragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragQuesForm1");
        if(quesForm1Fragment != null){
            fragmentTransaction.remove(quesForm1Fragment);
        }
        QuesForm2Fragment quesForm2Fragment = (QuesForm2Fragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragQuesForm2");
        if(quesForm2Fragment != null){
            fragmentTransaction.remove(quesForm2Fragment);
        }
        QuesForm3Fragment quesForm3Fragment = (QuesForm3Fragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragQuesForm3");
        if(quesForm3Fragment != null){
            fragmentTransaction.remove(quesForm3Fragment);
        }
        QuesForm4Fragment quesForm4Fragment = (QuesForm4Fragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragQuesForm4");
        if(quesForm4Fragment != null){
            fragmentTransaction.remove(quesForm4Fragment);
        }

    }

    private List<Question> createListQuestion() {
        List<Question> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            if(i==1){
                ArrayList<ContentQuestion> listQues = new ArrayList<>();
                listQues.add(new ContentQuestion("https://600tuvungtoeic.com/audio/abide_by.mp3",1));

                ArrayList<ChoiceAnswer> listAns = new ArrayList<>();
                listAns.add(new ChoiceAnswer("Abide by",1));
                listAns.add(new ChoiceAnswer("Agreement",2));
                listAns.add(new ChoiceAnswer("Assurance",3));
                listAns.add(new ChoiceAnswer("Cancellation",4));

                ArrayList<Integer> listAnsNoChoice = new ArrayList<>();
                listAnsNoChoice.add(1);
                list.add(new Question(2,"Question " + (i+1),listQues,listAns,listAnsNoChoice));

            }else if (i==2){
                ArrayList<ContentQuestion> listQues = new ArrayList<>();
                listQues.add(new ContentQuestion("Question",1));

                ArrayList<ChoiceAnswer> listAns = new ArrayList<>();
                listAns.add(new ChoiceAnswer("How old are you?",1));
                list.add(new Question(3, "Question " + (i + 1), listQues,listAns,new ArrayList<>()));
            }else if (i==3){
                ArrayList<ContentQuestion> listQues = new ArrayList<>();
                listQues.add(new ContentQuestion("How are you",1));

                ArrayList<ChoiceAnswer> listAns = new ArrayList<>();
                listAns.add(new ChoiceAnswer("How are you",1));
                list.add(new Question(4, "Question " + (i + 1), listQues,listAns,new ArrayList<>()));
            }else {
//                ArrayList<ContentQuestion> listQues = new ArrayList<>();
//                listQues.add(new ContentQuestion("How are you",1));
//
//                ArrayList<ChoiceAnswer> listAns = new ArrayList<>();
//                listAns.add(new ChoiceAnswer("How are you",1));
//                list.add(new Question(4, "Question " + (i + 1), listQues,listAns,new ArrayList<>()));
                ArrayList<ContentQuestion> listQues = new ArrayList<>();
                listQues.add(new ContentQuestion("Abide by",1));
                listQues.add(new ContentQuestion("Agreement",2));
                listQues.add(new ContentQuestion("Assurance",3));
                listQues.add(new ContentQuestion("Cancellation",4));


                ArrayList<ChoiceAnswer> listAns = new ArrayList<>();
                listAns.add(new ChoiceAnswer("https://600tuvungtoeic.com/audio/abide_by.mp3",1));
                listAns.add(new ChoiceAnswer("https://600tuvungtoeic.com/audio/agreement.mp3",2));
                listAns.add(new ChoiceAnswer("https://600tuvungtoeic.com/audio/assurance.mp3",3));
                listAns.add(new ChoiceAnswer("https://600tuvungtoeic.com/audio/cancellation.mp3",4));
                list.add(new Question(1, "Question " + (i + 1), listQues,listAns,new ArrayList<>()));
            }
        }

        return list;
    }
}