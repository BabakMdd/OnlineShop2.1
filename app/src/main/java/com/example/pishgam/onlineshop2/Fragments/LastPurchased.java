package com.example.pishgam.onlineshop2.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Adapters.CartAdapter;
import com.example.pishgam.onlineshop2.Adapters.LastPurcahsedAdapter;
import com.example.pishgam.onlineshop2.Models.CartItem;
import com.example.pishgam.onlineshop2.Models.Paid;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class LastPurchased extends Fragment{

    Context con;
    List<Paid> list;
    RecyclerView recyclerView;
    LastPurcahsedAdapter adapter;
    Setting setting=null;

    @SuppressLint("ValidFragment")
    public LastPurchased(Context con) {
        this.con=con;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(con);
        setting.setLocale("fa");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cart_paid_fragment,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.shopping_cart);
        LinearLayoutManager manager=new LinearLayoutManager(con,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        list=new ArrayList<>();

        adapter = new LastPurcahsedAdapter(con,list);

        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation = new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animation);

        addCart();

        recyclerView.setAdapter(adapter);

        //----------------------------------------Set Total Price-----------------------------------
        TextView total = (TextView)view.findViewById(R.id.title);
        int allprice = 0;
        for (Paid obj : list) {
            allprice+=obj.getPrice();
        }
        total.setText("مبلغ پرداخت شده"+": "+String.valueOf(allprice*CartAdapter.i)+" "+"تومان");

        return view;

    }

    private void addCart() {
        list.add(new Paid(R.drawable.man3,"پیراهن مردانه آستین دار",1,20000,"تسفیه"));
        list.add(new Paid(R.drawable.man3,"پیراهن مردانه آستین دار",1,20000,"تسفیه"));
        adapter.notifyDataSetChanged();
    }
}
