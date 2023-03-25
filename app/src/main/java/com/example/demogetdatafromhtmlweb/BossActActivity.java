package com.example.demogetdatafromhtmlweb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BossActActivity extends AppCompatActivity {

    TextView txtTitle, txtQuestion, txtBack;
    Button btnCont;
    ImageView imgBoss;
    Animation smalltobig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_act);

        smalltobig = AnimationUtils.loadAnimation(this,R.anim.smalltobig);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        txtTitle = (TextView) findViewById(R.id.textTitleBossAct);
        txtQuestion = (TextView) findViewById(R.id.textQuestionBossAct);
        txtBack = (TextView) findViewById(R.id.txtBackBossAct);
        btnCont = (Button) findViewById(R.id.btnContBossAct);
        imgBoss = (ImageView) findViewById(R.id.bigBoss);
        imgBoss.startAnimation(smalltobig);

        txtTitle.setTypeface(typeface);
        txtBack.setTypeface(typeface);
        txtQuestion.setTypeface(typeface);
        btnCont.setTypeface(typeface);

    }
}