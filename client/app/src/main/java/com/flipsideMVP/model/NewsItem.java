package com.flipsideMVP.model;

public class NewsItem {

    String newsItemTitle;
    int newsItemId;
    String newsSummary;
    String newsURL;
    String newsNameImg;

    public NewsItem(int newsItemId, String newsItemTitle, String newsSummary, String newsURL, String newsNameImg) {
        this.newsItemTitle = newsItemTitle;
        this.newsItemId = newsItemId;
        this.newsSummary = newsSummary;
        this.newsURL = newsURL;
        this.newsNameImg = newsNameImg;
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

    public String getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(String newsSummary) {
        this.newsSummary = newsSummary;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public String getNewsNameImg() {
        return newsNameImg;
    }

    public void setNewsNameImg(String newsNameImg) {
        this.newsNameImg = newsNameImg;
    }
}
