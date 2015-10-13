package com.tnaapp.tnalayout.tien;

/**
 * Created by XTien on 10/11/2015.
 */
public class Item {
    public String title ;
    public String description ;
    public String link ;
    public String pubDate ;
    public String summaryImg;

    public Item(String title, String description, String link, String pubDate, String summaryImg) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.summaryImg = summaryImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSummaryImg() {
        return summaryImg;
    }

    public void setSummaryImg(String summaryImg) {
        this.summaryImg = summaryImg;
    }
}
