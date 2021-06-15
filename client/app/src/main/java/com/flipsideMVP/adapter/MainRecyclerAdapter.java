package com.flipsideMVP.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flipsideMVP.R;
import com.flipsideMVP.model.AllNews;
import com.flipsideMVP.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<AllNews> allNewsList;

    public MainRecyclerAdapter(Context context, List<AllNews> allNewsList) {
        this.context = context;
        this.allNewsList = allNewsList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.newsCategoryTitle.setText(allNewsList.get(position).getNewsTitle());
        Picasso.get().load(allNewsList.get(position).getNewsImage()).into(holder.newsCategoryImage);
        setNewsItemRecycler(holder.itemRecycler, allNewsList.get(position).getNewsItemList());
    }

    @Override
    public int getItemCount() {
        return allNewsList.size();
    }

    private void setNewsItemRecycler(RecyclerView recyclerView, List<NewsItem> newsItemList){
        NewsItemRecyclerAdapter itemRecyclerAdapter = new NewsItemRecyclerAdapter(context, newsItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);

    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView newsCategoryTitle;
        ImageView newsCategoryImage;
        RecyclerView itemRecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            newsCategoryTitle = itemView.findViewById(R.id.newsCategoryTitle);
            newsCategoryImage = itemView.findViewById(R.id.newsImage);
            itemRecycler = itemView.findViewById(R.id.itemsRecycler);
        }
    }
}
