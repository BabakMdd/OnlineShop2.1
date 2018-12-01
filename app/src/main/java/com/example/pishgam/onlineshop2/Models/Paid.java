package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/19/2017.
 */

public class Paid {
    int img;
    String disc;
    int count;
    int price;
    String status;

    public Paid(int img, String disc, int count, int price, String status) {
        this.img = img;
        this.disc = disc;
        this.count = count;
        this.price = price;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
