package com.flipside.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flipside.R;
import com.flipside.model.NewsItem;

import java.util.List;

public class NewsItemRecyclerAdapter extends RecyclerView.Adapter<NewsItemRecyclerAdapter.NewsItemViewHolder> {

    private Context context;
    private List<NewsItem> newsItemList;

    public NewsItemRecyclerAdapter(Context context, List<NewsItem> newsItemList) {
        this.context = context;
        this.newsItemList = newsItemList;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsItemViewHolder(LayoutInflater.from(context).inflate(R.layout.news_row_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        holder.newsItemButton.setText(newsItemList.get(position).getNewsItemTitle());
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public static final class NewsItemViewHolder extends RecyclerView.ViewHolder{

        Button newsItemButton;

        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);

            newsItemButton = itemView.findViewById(R.id.newsButton);
        }
    }
}
