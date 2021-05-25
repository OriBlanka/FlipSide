package com.flipside.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.List;

public class AllNews {

    String newsTitle;
    String newsImage;
    List<NewsItem> newsItemList;

    public AllNews(String newsTitle, String newsImage, List<NewsItem> newsItemList) {
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
        this.newsItemList = newsItemList;
    }

    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }
}
