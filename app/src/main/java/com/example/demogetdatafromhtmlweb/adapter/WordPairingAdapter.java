package com.example.demogetdatafromhtmlweb.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.my_interface.IClickItemWordPairingListener;

public class WordPairingAdapter extends BaseAdapter {

    private Context context;
    private String[] listWord;
    private IClickItemWordPairingListener iClickItemWordPairingListener;

    public WordPairingAdapter(Context context, String[] listWord, IClickItemWordPairingListener iClickItemWordPairingListener) {
        this.context = context;
        this.listWord = listWord;
        this.iClickItemWordPairingListener = iClickItemWordPairingListener;
    }

    @Override
    public int getCount() {
        return listWord.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.gv_word_pairing_item,null);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/FredokaOneRegular.ttf");

        TextView txtWord = view.findViewById(R.id.txtWord);

        txtWord.setTextSize(20);
//        txtWord.setTypeface(typeface);
        txtWord.setText(listWord[i]);

        txtWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemWordPairingListener.onClickItemWordPairing(txtWord,listWord[i]);
            }
        });

        return view;
    }
}
