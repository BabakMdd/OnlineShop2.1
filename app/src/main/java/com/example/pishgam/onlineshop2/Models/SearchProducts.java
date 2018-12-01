package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/13/2017.
 */

public class SearchProducts extends ParentView {

    String title;
    String price;
    int img;

    public SearchProducts(String title, String price, int img) {
        super(ParentView.VIEW);
        this.title = title;
        this.price = price;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
