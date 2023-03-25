package com.example.demogetdatafromhtmlweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestScreenActivity extends AppCompatActivity {

    Button btnVocab;
    Button btnSentence;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);

        btnSentence = findViewById(R.id.btnSentence);
        btnVocab = findViewById(R.id.btnVocabulary);
        btnTest = findViewById(R.id.btnTestBasic);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestScreenActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        btnVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestScreenActivity.this,TopicActivity.class);
                startActivity(intent);
            }
        });
        btnSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestScreenActivity.this,ConversationTopicActivity.class);
                startActivity(intent);
            }
        });
    }
}