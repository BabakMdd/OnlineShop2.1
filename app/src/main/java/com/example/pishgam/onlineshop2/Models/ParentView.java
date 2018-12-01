package com.example.pishgam.onlineshop2.Models;

/**
 * Created by Babak on 9/10/2017.
 */

public class ParentView {

    public static final int LOADING=1;
    public static final int VIEW=2;

    int currentType;

    public ParentView(int currentType) {
        this.currentType = currentType;
    }

    public int getCurrentType() {
        return currentType;
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
    }
}
