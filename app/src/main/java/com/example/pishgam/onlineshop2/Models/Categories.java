package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/13/2017.
 */

public class Categories {

    String title;
    int image;

    public Categories(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
