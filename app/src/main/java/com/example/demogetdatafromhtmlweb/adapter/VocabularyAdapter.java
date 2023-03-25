package com.example.demogetdatafromhtmlweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.model.Vocabulary;
import com.example.demogetdatafromhtmlweb.my_interface.IClickItemVocab;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyViewholder>{

    List<Vocabulary> listVocab;
    private IClickItemVocab iClickItemVocab;
    Context mContext;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    public VocabularyAdapter(List<Vocabulary> listVocab, IClickItemVocab iClickItemVocab,Context context) {
        this.listVocab = listVocab;
        this.iClickItemVocab = iClickItemVocab;
        this.mContext = context;
    }

    public void setData(List<Vocabulary> list){
        this.listVocab = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VocabularyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_item_layout,parent,false);
        return new VocabularyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyViewholder holder, @SuppressLint("RecyclerView") int position) {
        Vocabulary vocabulary = listVocab.get(position);
        if(vocabulary == null)
            return;
        Picasso.with(mContext).load(vocabulary.getImages()).into(holder.imgVocab);
        holder.txtContent.setText(vocabulary.getContent());
        holder.txtPron.setText(vocabulary.getPronounce());

        holder.ibtnLoud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, vocabulary.getSpeaker(), Toast.LENGTH_SHORT).show();
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.setDataSource(vocabulary.getSpeaker());
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.ibtnSTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    iClickItemVocab.onClickItemVocab(vocabulary,position);
            }
        });
    }



    @Override
    public int getItemCount() {
        if(listVocab != null)
        return listVocab.size();
        else
        return 0;
    }


    public class VocabularyViewholder extends RecyclerView.ViewHolder {

        ImageView imgVocab;
        TextView txtContent;
        TextView txtPron;
        ImageButton ibtnLoud;
        ImageButton ibtnSTT;

        public VocabularyViewholder(@NonNull View itemView) {
            super(itemView);

            imgVocab = itemView.findViewById(R.id.img_vocab);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtPron = itemView.findViewById(R.id.txtPronounce);
            ibtnLoud = itemView.findViewById(R.id.ibtnLoadSpeaker);
            ibtnSTT = itemView.findViewById(R.id.ibtnSTT);
        }
    }

}
