package com.example.xihad.pixlups;

public class CartData {


    String caption,imageuri,price, orderid,trxid,status,newid,usid,upuserid;

    public CartData() {
    }


    public CartData(String orderid, String trxid, String newid, String usid,String upuserid,String price) {
        this.orderid = orderid;
        this.trxid = trxid;
        this.newid = newid;
        this.usid = usid;
        this.upuserid=upuserid;
        this.price=price;
    }

    public CartData(String caption, String imageuri, String price, String status, String newid) {
        this.caption = caption;
        this.imageuri = imageuri;
        this.price = price;
        this.status = status;
        this.newid = newid;
    }

    public String getUpuserid() {
        return upuserid;
    }

    public void setUpuserid(String upuserid) {
        this.upuserid = upuserid;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewid() {
        return newid;
    }

    public void setNewid(String newid) {
        this.newid = newid;
    }
}
