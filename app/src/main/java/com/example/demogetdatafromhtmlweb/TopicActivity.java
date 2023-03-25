package com.example.demogetdatafromhtmlweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.translation.Translator;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demogetdatafromhtmlweb.adapter.TopicsAdapter;
import com.example.demogetdatafromhtmlweb.model.Topics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity {

    private RecyclerView rcvTopic;
    private TopicsAdapter topicsAdapter;

    private ArrayList<Integer> list_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        rcvTopic = findViewById(R.id.rcv_topic);
        topicsAdapter = new TopicsAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvTopic.setLayoutManager(gridLayoutManager);

        CreateImageSVGArray();

        topicsAdapter.setData(getListTopicToeic());
        rcvTopic.setAdapter(topicsAdapter);



    }

    private void CreateImageSVGArray() {
        list_Img = new ArrayList<>();
        list_Img.add(R.drawable.ic_animals_1);
        list_Img.add(R.drawable.ic_appearance_1);
        list_Img.add(R.drawable.ic_communication);
        list_Img.add(R.drawable.ic_culture);
        list_Img.add(R.drawable.ic_food_and_drink);
        list_Img.add(R.drawable.ic_functions);
        list_Img.add(R.drawable.ic_health);
        list_Img.add(R.drawable.ic_homes_and_buildings);
        list_Img.add(R.drawable.ic_leisure);
        list_Img.add(R.drawable.ic_notions);
        list_Img.add(R.drawable.ic_people);
        list_Img.add(R.drawable.ic_politics_and_society);
        list_Img.add(R.drawable.ic_science_and_technology);
        list_Img.add(R.drawable.ic_sport);
        list_Img.add(R.drawable.ic_the_natural_world);
        list_Img.add(R.drawable.ic_time_and_space);
        list_Img.add(R.drawable.ic_travel);
        list_Img.add(R.drawable.ic_work_and_business);
    }
    private List<Topics> getListTopicOxford(){
        List<Topics> topicsList = new ArrayList<>();

        String url = "https://www.oxfordlearnersdictionaries.com/topic/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = "";
                String url = "";
                Document doc = Jsoup.parse(response);
                if(doc != null){
                    Elements elements = doc.select("div.topic-box");

                    int indexImgList = 0;

                    for (Element element :elements){
                        Element elementUrlCat = element.getElementsByTag("a").first();
                        Element elementName = element.select("div.topic-label").first();

                        if(elementName != null){
                            name = elementName.text();
                        }
                        if(elementUrlCat != null){
                            url = elementUrlCat.attr("href");
                        }
                        //topicsList.add(new Topics(name,list_Img.get(indexImgList),url));

                        indexImgList++;
                    }
                    topicsAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TopicActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

        return topicsList;
    }
    private List<Topics> getListTopicToeic(){
        List<Topics> topicsList = new ArrayList<>();

        String url = "https://600tuvungtoeic.com/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = "";
                String urlTopics = "";
                String img = "";
                Document doc = Jsoup.parse(response);
                if(doc != null){
                    Elements elements = doc.select("div.col-md-2");

                    int indexTopics = 1;
                    for (Element element :elements){

                        Element elementName = element.getElementsByTag("h3").first();
                        Element elementImg = element.getElementsByTag("img").first();

                        if(elementName != null){
                            name = elementName.text();
                        }
                        urlTopics = url + "index.php?mod=lesson&id=" + indexTopics;

                        if(elementImg != null){
                            img = elementImg.attr("src");
                        }
                        topicsList.add(new Topics(name,img,urlTopics));
                        indexTopics++;
                    }
                    topicsAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TopicActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

        return topicsList;
    }
}