package com.example.demogetdatafromhtmlweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demogetdatafromhtmlweb.model.Conversation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    TextView txtTitleConver,txtQuesT;
    ListView lvConver;
    Button button;
    ImageButton ibtnRecord;

    static  ArrayList<String> listAudio;
    static ArrayList<String> listConver;
    static ArrayList<Conversation> listSentenInConver;


    ArrayAdapter adapter;
    SpeechRecognizer speechRecognizer;

    static int checkRecord = 0;
    static int indexOfList = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        AnhXa();
        ArrayList<String> listMain = new ArrayList<>();

        // Check permisson
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Get Intent dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        String url = bundle.getString("urlConversation");
        String topic = bundle.getString("nameTopicConver");
        int indexOfTopicConver = bundle.getInt("indexTopicConver",0);

        // Xử lý title of Conversation
        String title = "Lesson " + indexOfTopicConver + ", Anna and John are talking about " + topic + " topic.";
        SpannableString spannable = new SpannableString(title);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 19, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 42, 42 + topic.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtTitleConver.setText(spannable);

        // Get dữ liệu from website
        getListConversation(url);
        loadData();
//        System.out.println("size out" + listConver.size());

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listMain);
        lvConver.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.setDataSource(listSentenInConver.get(indexOfList).getListMP3());
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            button.setEnabled(false);
                            lvConver.smoothScrollToPosition(indexOfList);
                            mp.start();
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            if(listSentenInConver.get(indexOfList).getTypeConverSenten() == 0){
                                button.setEnabled(true);
                            }else {
                                ibtnRecord.setVisibility(View.VISIBLE);
                                txtQuesT.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(indexOfList < listConver.size()){
                    listMain.add(listConver.get(indexOfList));
                    adapter.notifyDataSetChanged();
                    indexOfList++;
                }
            }
        });

        ibtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkRecord == 0){
                    ibtnRecord.setImageDrawable(getDrawable(R.drawable.ic_mic_off));

                    // startlistening
                    speechRecognizer.startListening(intent);
                    checkRecord = 1;
                }else {
                    ibtnRecord.setImageDrawable(getDrawable(R.drawable.ic_mic));

                    //stop listening
                    speechRecognizer.stopListening();
                    checkRecord = 0;
                }
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

//                Toast.makeText(ConversationActivity.this,"vô đây",Toast.LENGTH_SHORT).show();
//                Log.d("AAA","chuỗi gốc " + listSentenInConver.get(indexOfList).getListSentence()
//                + " chuỗi record " + data.get(0));
                String sentenRecord = data.get(0);
                String contentSenten = listSentenInConver.get(indexOfList-1).getListSentence();
                char[] sentenRecordCharArr = sentenRecord.toCharArray();
                char[] sentenDBCharArr = contentSenten.toCharArray();
                Toast.makeText(ConversationActivity.this,data.get(0),Toast.LENGTH_LONG).show();
                SpannableString spannable = new SpannableString(sentenRecord);
                if(sentenRecord.length() > contentSenten.length()){
                    for (int i=0;i<contentSenten.length();i++){
                        if(sentenDBCharArr[i] == sentenDBCharArr[i]){
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else {
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                    spannable.setSpan(new ForegroundColorSpan(Color.RED),contentSenten.length(),sentenRecord.length()-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    ShowFailedDialog(spannable);
                }else if(sentenRecord.length() < contentSenten.length()){
                    for (int i=0;i<sentenRecord.length();i++){
                        if(sentenRecordCharArr[i] == sentenDBCharArr[i]){
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else {
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }

                    ShowFailedDialog(spannable);
                }else {
                    boolean passOrFaill = true;
                    for (int i=0;i<contentSenten.length();i++){
                        if(sentenRecordCharArr[i] == sentenDBCharArr[i]){
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else {
                            if(passOrFaill)
                                passOrFaill = false;
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                    if(passOrFaill)
                        ShowCorrectDialog(indexOfList-1);
                    else   ShowFailedDialog(spannable);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ConversationActivity.this, "Permisson granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ConversationActivity.this, "Permisson denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void AnhXa() {
        lvConver = findViewById(R.id.lvConver);
        button = findViewById(R.id.btnClickme);
        txtTitleConver = findViewById(R.id.txtTitleConver);
        txtQuesT = findViewById(R.id.txtQuesT);
        ibtnRecord = findViewById(R.id.ibtnRecord);
    }

    private void loadDataConver() {

        File path = getApplication().getFilesDir();
        File readFrom = new File(path,"listConver.txt");
        byte[] conver = new byte[(int) readFrom.length()];

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(readFrom);
            fileInputStream.read(conver);

            String s = new String(conver);

            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", Q");
            listConver = new ArrayList<>(Arrays.asList(split));

            for (String converS : listConver){
                if (converS.equals(" ") || converS.startsWith("Q")){
                    listConver.remove(converS);
                }
                if(converS.startsWith("Q")){
                    converS.substring(2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                lvConver.smoothScrollToPosition(pos);

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void getListConversation(String url) {
        listAudio = new ArrayList<>();
        listConver = new ArrayList<>();

        Toast.makeText(ConversationActivity.this, url, Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                if(document != null){
                    Element listElementDiv = document.select("div.awr").first();
                        listConver.clear();
                        for (Node node :listElementDiv.childNodes()){
                            if (node instanceof TextNode){
                                listConver.add(((TextNode)node).text());
                                System.out.println("line " + ((TextNode)node).text());
                                }
                        }
                        Elements elementDivAudio = listElementDiv.select("div.sc_player_container1");
                        for (Element element1 : elementDivAudio){
                            Element elementAduio = element1.select("input.myButton_play").first();
                            if(elementAduio != null){
                                String linkAudio = elementAduio.attr("onclick");
                                String[] listWord = linkAudio.split("'");
                                listAudio.add(listWord[5]);
                            }
                        }




                }
                listConver.remove(0);
                listConver.remove(listConver.size()-1);

                removeArr();
                Log.d("AAA", "size conver in" + listConver.size());
                Log.d("AAA", "size audio in" + listAudio.size());

                listSentenInConver = new ArrayList<>();
                int type;
                for (int i=0;i<listConver.size();i++){
                    if(listConver.get(i).contains("?")){
                        type = 1;
                    }else {
                        type = 0;
                    }
                    listSentenInConver.add(new Conversation(type,listAudio.get(i),listConver.get(i)));
                }

                Log.d("AAA", "size conver in" + listSentenInConver.size());
                saveData(listSentenInConver);
//                writeDataConverToLoad(listConver);
//                writeDataAudioToLoad(listAudio);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void saveData(ArrayList<Conversation> listConver1){
        System.out.println("size in" + listConver1.size());
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listConver1);
        editor.putString("task list",json);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<Conversation>>() {}.getType();

        listSentenInConver = gson.fromJson(json,type);

        if(listSentenInConver == null){
            listSentenInConver = new ArrayList<>();
        }

        System.out.println("size out" + listSentenInConver.size());
    }

    private void removeArr(){
        Iterator<String> iterator = listConver.iterator();
        while (iterator.hasNext()) {
            String language = iterator.next();
            if (language.length() < 7) {
                // languages.remove(language); // Don't use ArrayList.remove()
                iterator.remove();
            }
        }
        System.out.println("list to string" + listConver.toString());
    }

    private void writeDataAudioToLoad(ArrayList<String> listAudio) {
        File path = getApplication().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path,"listAudio.txt"));
            writer.write(listConver.toString().getBytes());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeDataConverToLoad(ArrayList<String> listConver) {

        File path = getApplication().getFilesDir();
        File readFrom = new File(path,"listConver.txt");

        PrintWriter writerPrint = null;
        try {
            writerPrint = new PrintWriter(readFrom);
            writerPrint.print("");
            writerPrint.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream writer = new FileOutputStream(new File(path,"listConver.txt"));
            writer.write(listConver.toString().getBytes());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        indexOfList = 0;
        Toast.makeText(ConversationActivity.this,"clear",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ConversationActivity.this,ConversationTopicActivity.class);
        startActivity(intent);
        finish();
    }
}