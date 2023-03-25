package com.example.demogetdatafromhtmlweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demogetdatafromhtmlweb.fragmentUI.QuesForm4Fragment;
import com.example.demogetdatafromhtmlweb.fragmentUI.StartUpDataFragment;
import com.example.demogetdatafromhtmlweb.fragmentUI.WordPairingFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button bottomsheet;
    // text view and two buttons.
    private EditText edtLanguage;
    private TextView translateLanguageTV;
    private Button translateLanguageBtn;

    FirebaseTranslatorOptions options =
            new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.VI)
                    .build();
    final FirebaseTranslator englishGermanTranslator =
            FirebaseNaturalLanguage.getInstance().getTranslator(options);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.layout_main,new StartUpDataFragment(),"frag");
//        fragmentTransaction.commit();

//        Intent intent = new Intent(MainActivity.this,TestActivity.class);
//        startActivity(intent);

        bottomsheet = findViewById(R.id.botttom_sheet);
        bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });

// on below line we are initializing our variables.
        edtLanguage = findViewById(R.id.idEdtLanguage);
        translateLanguageTV = findViewById(R.id.idTVTranslatedLanguage);
        translateLanguageBtn = findViewById(R.id.idBtnTranslateLanguage);

        // adding on click listener for button
        translateLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to download language
                // modal to which we have to translate.
                String string = edtLanguage.getText().toString();
                Toast.makeText(MainActivity.this, "CLick", Toast.LENGTH_SHORT).show();
                downloadModal(string);
//                translateLanguage(string);
            }
        });
    }

    private void downloadModal(String input) {
        // below line is use to download the modal which
        // we will require to translate in german language
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();

        // below line is use to download our modal.
        englishGermanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // this method is called when modal is downloaded successfully.
                Toast.makeText(MainActivity.this, "Please wait language modal is being downloaded.", Toast.LENGTH_SHORT).show();

                // calling method to translate our entered text.
                translateLanguage(input);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to download modal", Toast.LENGTH_SHORT).show();
            }
        });
        SpannableString spannableString = new SpannableString("k");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
//        FirebaseModelManager modelManager = FirebaseModelManager.getInstance();
//
//// Get translation models stored on the device.
//        modelManager.getDownloadedModels(FirebaseTranslateRemoteModel.class)
//                .addOnSuccessListener(new OnSuccessListener<Set<FirebaseTranslateRemoteModel>>() {
//                    @Override
//                    public void onSuccess(Set<FirebaseTranslateRemoteModel> models) {
//                        // ...
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Error.
//                    }
//                });
//
//// Delete the German model if it's on the device.
//        FirebaseTranslateRemoteModel deModel =
//                new FirebaseTranslateRemoteModel.Builder(FirebaseTranslateLanguage.VI).build();
//        modelManager.deleteDownloadedModel(deModel)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void v) {
//                        // Model deleted.
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Error.
//                    }
//                });
//
//// Download the French model.
//        FirebaseTranslateRemoteModel frModel =
//                new FirebaseTranslateRemoteModel.Builder(FirebaseTranslateLanguage.EN).build();
//        FirebaseModelDownloadConditions conditions1 = new FirebaseModelDownloadConditions.Builder()
//                .requireWifi()
//                .build();
//        modelManager.download(frModel, conditions)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void v) {
//                        // Model downloaded.
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Error.
//                    }
//                });
    }

    private void translateLanguage(String input) {
        englishGermanTranslator.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                translateLanguageTV.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.bottomsheetlayout);


        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}