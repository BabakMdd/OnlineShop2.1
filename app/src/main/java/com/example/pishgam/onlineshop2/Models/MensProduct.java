package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/16/2017.
 */

public class MensProduct extends ParentView {

    int img;
    String discription;

    public MensProduct(int img,String disc) {
        super(ParentView.VIEW);
        this.img=img;
        this.discription=disc;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }


}
