package com.example.demogetdatafromhtmlweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demogetdatafromhtmlweb.adapter.VocabularyAdapter;
import com.example.demogetdatafromhtmlweb.model.Vocabulary;
import com.example.demogetdatafromhtmlweb.my_interface.IClickItemVocab;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VocabularyActivity extends AppCompatActivity {

    RecyclerView rcvVocab;
    VocabularyAdapter vocabularyAdapter;

    public static String contentWordVocab;
    public static int vocabId;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        rcvVocab = findViewById(R.id.rcv_vocab);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        String url = bundle.getString("urlCat");
        getListVocabulary(url);
        vocabularyAdapter = new VocabularyAdapter(getListVocabulary(url), new IClickItemVocab() {
            @Override
            public void onClickItemVocab(Vocabulary vocab, int pos) {
                promptSpeechInput();
                contentWordVocab = vocab.getContent();
                vocabId = pos;
            }
        }, this);

//        HScrollManager linearLayout = new HScrollManager(this,RecyclerView.HORIZONTAL,false);
//        linearLayout.setScrollingEnabled(true);
//        rcvVocab.setLayoutManager(linearLayout);

        vocabularyAdapter.setData(getListVocabulary(url));
        rcvVocab.setAdapter(vocabularyAdapter);

        //setSnapHelper();
    }

    private void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rcvVocab);
    }

    private List<Vocabulary> getListVocabulary(String urlVocab) {
        List<Vocabulary> list = new ArrayList<>();

        String url = urlVocab;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String content = "";
                String img = "";
                String pron = "";
                //String speaker = "https://600tuvungtoeic.com/audio/";

                Document doc = Jsoup.parse(response);
                if(doc != null){
                    Elements elementCat = doc.select("div.tuvung");


                    for (Element element :elementCat){
                        String speaker = "https://600tuvungtoeic.com/audio/";
                        String contentNew = "";
                        Element elementContent = element.select("span[style*=color: blue]").first();
                        Element elementPron = element.select("span[style*=color: red]").first();
                        Element elementImg = element.getElementsByTag("img").first();
                        Element elementSpeaker = element.select("audio").first();

                       // Element elementSpeaker = element.select("div.noidung").first();

                        if(elementContent != null){
                            content = elementContent.text();
                        }
                        if(elementPron != null){
                            pron = elementPron.text();
                        }
                        if(elementImg != null){
                            img = elementImg.attr("src");
                        }
                        if(elementSpeaker != null){
                            contentNew = content.replace(' ','_');
                            speaker += contentNew + ".mp3";

                        }

                        list.add(new Vocabulary(content,img,pron,speaker));

                    }
                   // vocabularyAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VocabularyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

        return list;
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
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
//    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                        ShowFailedDialog(spannable);
                    }else if(vocabRecord.length() < contentWordVocab.length()){
                        for (int i=0;i<vocabRecord.length();i++){
                            if(vocabRecordCharArr[i] == vocabDBCharArr[i]){
                                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }else {
                                spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        ShowFailedDialog(spannable);
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

                        if(passOrFaill)
                            ShowCorrectDialog(vocabId);
                        else   ShowFailedDialog(spannable);
                    }
                }
                break;
            }
        }
    }

    private void ShowFailedDialog(SpannableString word) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.failed_ques_dialog_layout);

        TextView txtWordSTT = dialog.findViewById(R.id.txtContent);
        Button btnRestart  = dialog.findViewById(R.id.btnRestart);

        txtWordSTT.setText(word);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void ShowCorrectDialog(int pos) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pass_ques_dialog_layout);

        Button btnNext = dialog.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvVocab.smoothScrollToPosition(pos + 1);

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private static class HScrollManager extends LinearLayoutManager {
        private boolean scrollingEnabled = true;

        public HScrollManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public void setScrollingEnabled(boolean enabled) {
            scrollingEnabled = enabled;
        }

        @Override
        public boolean canScrollHorizontally() {
            return scrollingEnabled && super.canScrollHorizontally();
        }
    }
}
