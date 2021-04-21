package com.flipside.model;

public class NewsItem {

    String newsItemTitle;
    int newsItemId;

    public NewsItem(int newsItemId, String newsItemTitle) {
        this.newsItemTitle = newsItemTitle;
        this.newsItemId = newsItemId;
    }

    public String getNewsItemTitle() {
        return newsItemTitle;
    }

    public void setNewsItemTitle(String newsItemTitle) {
        this.newsItemTitle = newsItemTitle;
    }

    public int getNewsItemId() {
        return newsItemId;
    }

    public void setNewsItemId(int newsItemId) {
        this.newsItemId = newsItemId;
    }
}
