package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/17/2017.
 */

public class CartItem {
    int img;
    String disc;
    int count;
    int price;

    public CartItem(String disc,int img,int count,int price) {
        //super(ParentView.VIEW);
        this.img=img;
        this.disc=disc;
        this.count=count;
        this.price=price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
