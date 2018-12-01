package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/10/2017.
 */

public class Brands extends ParentView {
    int img;

    public Brands(int img) {
        super(ParentView.VIEW);
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
