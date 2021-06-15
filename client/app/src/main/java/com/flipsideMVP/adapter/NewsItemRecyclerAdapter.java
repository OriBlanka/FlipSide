package com.flipsideMVP.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flipsideMVP.R;
import com.flipsideMVP.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsItemRecyclerAdapter extends RecyclerView.Adapter<NewsItemRecyclerAdapter.NewsItemViewHolder> {

    private Context context;
    private List<NewsItem> newsItemList;
    private static final String TAG = "Item RecyclerView";

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
        Picasso.get().load(newsItemList.get(position).getNewsNameImg()).into(holder.webNameImage);
        holder.newsItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.news_summary_popup, null);
                TextView newsTitle = dialogView.findViewById(R.id.newsTitle);
                TextView newsSummary = dialogView.findViewById(R.id.newsSummary);
                TextView newsUrl = dialogView.findViewById(R.id.fullNewsURL);
                TextView closeButton = dialogView.findViewById(R.id.closeButton);

                newsTitle.setText(newsItemList.get(position).getNewsItemTitle());
                newsSummary.setText(newsItemList.get(position).getNewsSummary());
                newsUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = newsItemList.get(position).getNewsURL();
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(webIntent);
                    }
                });

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public static final class NewsItemViewHolder extends RecyclerView.ViewHolder{

        Button newsItemButton;
        ImageView webNameImage;

        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);

            newsItemButton = itemView.findViewById(R.id.newsButton);
            webNameImage = itemView.findViewById(R.id.webImageView);

        }
    }
}
