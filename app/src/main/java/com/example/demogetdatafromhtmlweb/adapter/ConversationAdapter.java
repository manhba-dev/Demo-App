package com.example.demogetdatafromhtmlweb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.model.ConversationTopic;

import java.util.List;

public class ConversationAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ConversationTopic> conversations;

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public ConversationAdapter(Context context, int layout, List<ConversationTopic> conversations) {
        this.context = context;
        this.layout = layout;
        this.conversations = conversations;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(layout,null);

        TextView txtConverTopic = view.findViewById(R.id.txtConverTopic);

        ConversationTopic conversationTopic = conversations.get(i);
        txtConverTopic.setText(conversationTopic.getContentTopic());

        return view;
    }
}
