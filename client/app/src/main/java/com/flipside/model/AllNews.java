package com.flipside.model;

import java.util.List;

public class AllNews {

    String newsTitle;
    int newsImage;
    List<NewsItem> newsItemList;

    public AllNews(String newsTitle, int newsImage, List<NewsItem> newsItemList) {
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

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }
}
