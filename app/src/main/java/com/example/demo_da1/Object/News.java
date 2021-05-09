package com.example.demo_da1.Object;

import java.io.Serializable;

public class News implements Serializable {
    public String id,title,link,image,time;

    public News(String id, String title, String link, String image, String time) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.image = image;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public News(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public News() {


    }

    public News(String tieude, String link, String anh, String thoigiancn) {
        this.title = tieude;
        this.link = link;
        this.image = anh;
        this.time = thoigiancn;
    }

    }

