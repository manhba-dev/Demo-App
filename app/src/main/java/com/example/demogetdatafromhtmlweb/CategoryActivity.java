package com.example.demogetdatafromhtmlweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demogetdatafromhtmlweb.adapter.CategoryAdapter;
import com.example.demogetdatafromhtmlweb.model.Category;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rcv_cat;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        rcv_cat = findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        String url = bundle.getString("urlCat");

        LinearLayoutManager linearLayout = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv_cat.setLayoutManager(linearLayout);

        categoryAdapter.setData(getListCategoryOxford(url));
        rcv_cat.setAdapter(categoryAdapter);
    }

    private List<Category> getListCategoryOxford(String urlCat) {
        List<Category> list = new ArrayList<>();

        String url = urlCat;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = "";

                Document doc = Jsoup.parse(response);
                if(doc != null){
                    Elements elementCat = doc.select("div.topic-box");

                    for (Element element :elementCat){
                        Element elementName = element.getElementsByTag("a").first();
                        if(elementName != null){
                            name = elementName.text();
                        }

                        Element elementsCatList = element.select("div.l3").first();
                        Toast.makeText(CategoryActivity.this, elementsCatList.text(), Toast.LENGTH_SHORT).show();
                        Elements elementsList = elementsCatList.getElementsByTag("a");

                        List<String> listCat = new ArrayList<>();
                        for (Element elementList :elementsList){
                            listCat.add(elementList.text());
                        }
                        //list.add(new Category(name,listCat));

                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

        return list;
    }

}