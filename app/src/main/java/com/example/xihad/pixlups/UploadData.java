package com.example.xihad.pixlups;

public class UploadData {



    private String caption, category, price, description, imageuri, userid, id;
    private int priority, likecount;

    public UploadData() {
    }

    public UploadData(String cation, String category, String price, String description, String imageuri, String userid, int priority, String id, int likecount) {

        this.caption = cation;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageuri = imageuri;
        this.userid = userid;
        this.priority = priority;
        this.id = id;
        this.likecount = likecount;
    }

    public UploadData(String caption, String price, String imageuri) {
        this.caption = caption;
        this.price = price;
        this.imageuri = imageuri;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = 0;
    }
}
