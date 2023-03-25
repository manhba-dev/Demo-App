package com.example.demogetdatafromhtmlweb.adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.VocabularyActivity;
import com.example.demogetdatafromhtmlweb.model.Topics;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {

    private Context mContext;
    private List<Topics> list_topic;

    public TopicsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item_layout,parent,false);
        return new TopicViewHolder(view);
    }

    // set dữ liệu hiển thị lên
    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topics topics = list_topic.get(position);
        if (topics == null){
            return;
        }
       // holder.imgTopic.setImageResource(topics.getTopicImage());
        holder.txtNameTopic.setText(topics.getTopicName());
        Picasso.with(mContext).load(topics.getTopicImage()).into(holder.imgTopic);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VocabularyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("urlCat",topics.getUrlCat());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list_topic != null)
        return list_topic.size();
        else return 0;
    }

    public void setData(List<Topics> topicsList){
        this.list_topic = topicsList;
        notifyDataSetChanged();
    }


    public class TopicViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameTopic;
        private ImageView imgTopic;
        private CardView cardView;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameTopic = itemView.findViewById(R.id.txtNameTopic);
            imgTopic = itemView.findViewById(R.id.img_topic);
            cardView = itemView.findViewById(R.id.topicItem);
        }
    }
}
