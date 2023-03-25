package com.example.demogetdatafromhtmlweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demogetdatafromhtmlweb.adapter.ConversationTopicAdapter;
import com.example.demogetdatafromhtmlweb.model.ConversationTopic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ConversationTopicActivity extends AppCompatActivity {

    ListView lvConverTopic;
    ArrayList<ConversationTopic> listConverTopic;
    ConversationTopicAdapter listTopicConverAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_topic);

        lvConverTopic = findViewById(R.id.lvSentTopic);


        listTopicConverAdapter = new ConversationTopicAdapter(this, R.layout.conver_topic_item_layout, getListConverTopic());
        lvConverTopic.setAdapter(listTopicConverAdapter);

        lvConverTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ConversationTopicActivity.this,ConversationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("urlConversation",listConverTopic.get(i).getLinkTopic());
                bundle.putInt("indexTopicConver",i+1);
                bundle.putString("nameTopicConver",listConverTopic.get(i).getContentTopic());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    private ArrayList<ConversationTopic> getListConverTopic() {
        listConverTopic = new ArrayList<>();

        String url = "https://basicenglishspeaking.com/daily-english-conversation-topics/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                if(document != null){
                    Elements elementsListTopic = document.select("div.tcb-flex-col");

                    for (Element topic : elementsListTopic){
                        Elements listContentTopic = topic.getElementsByTag("a");
                        String topicConver = "";
                        String linkTopic = "";
                        for (Element contenTopic : listContentTopic){
                            if(contenTopic != null){
                                topicConver = contenTopic.text();
                                linkTopic = contenTopic.attr("href");
                            }
                            if(topicConver.equals("Family") || topicConver.equals("Books")){
                                continue;
                            }
                            listConverTopic.add(new ConversationTopic(linkTopic,topicConver));
                        }
                    }
                    listTopicConverAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConversationTopicActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
        return listConverTopic;
    }
}