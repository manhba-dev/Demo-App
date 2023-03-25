package com.example.demogetdatafromhtmlweb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demogetdatafromhtmlweb.R;
import com.example.demogetdatafromhtmlweb.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder>{
    private Context context;
    private List<Category> listCat;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> list){
        this.listCat = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout,parent,false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        Category category = listCat.get(position);
        if(category == null){
            return;
        }
        holder.txtNameCat.setText(category.getCatName());

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1,category.getListCat());
        holder.rcvCat.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        if(listCat != null)
        return listCat.size();
        else return 0;
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder{

        private TextView txtNameCat;
        private ListView rcvCat;

        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);

            txtNameCat = itemView.findViewById(R.id.txtCat);
            rcvCat = itemView.findViewById(R.id.rcv_catList);

        }
    }
}
