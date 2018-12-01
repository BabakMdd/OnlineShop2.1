package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/10/2017.
 */

public class NewProducts extends ParentView {

    int img;
    String disc;
    String price;

    public NewProducts( int img, String price, String disc) {
        super(ParentView.VIEW);
        this.img = img;
        this.price = price;
        this.disc = disc;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
