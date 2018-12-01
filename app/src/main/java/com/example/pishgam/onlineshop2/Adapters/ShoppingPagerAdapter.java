package com.example.pishgam.onlineshop2.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pishgam.onlineshop2.Fragments.LastPurchased;
import com.example.pishgam.onlineshop2.Fragments.Purchase;

public class ShoppingPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    Context con;

    //Constructor to the class
    public ShoppingPagerAdapter(FragmentManager fm, int tabCount,Context con) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.con=con;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
               LastPurchased tab1 = new LastPurchased(con);
                return tab1;
            case 1:
                Purchase tab2 = new Purchase(con);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
