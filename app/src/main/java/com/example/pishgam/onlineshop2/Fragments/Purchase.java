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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Activities.ShopingCart;
import com.example.pishgam.onlineshop2.Adapters.CartAdapter;
import com.example.pishgam.onlineshop2.Models.CartItem;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Purchase extends Fragment {

    Context con;
    List<CartItem> list;
    RecyclerView recyclerView;
    LinearLayout pay;
    CartAdapter adapter;
    Setting setting=null;

    @SuppressLint("ValidFragment")
    public Purchase(Context con) {
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
        View view=inflater.inflate(R.layout.cart_paymnet_fragment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.shopping_cart);
        pay = (LinearLayout)view.findViewById(R.id.btn_pay);
        LinearLayoutManager manager = new LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();

        adapter = new CartAdapter(list, con);

        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation = new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animation);

        //---------------------------Pay Cart Btn--------------------------------------
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting = new Setting(con);
                setting.message("Pay clicked");
            }
        });
        recyclerView.setAdapter(adapter);
        addCart();

        //----------------------------------------Set Total Price-----------------------------------
        TextView total = (TextView)view.findViewById(R.id.total_price);
        int allprice = 0;
        for (CartItem obj : list) {
            allprice+=obj.getPrice();
        }
        total.setText("مبلغ قابل پرداخت"+": "+String.valueOf(allprice*CartAdapter.i)+" "+"تومان");

        return view;
    }

    private void addCart() {
        list.add(new CartItem("پیراهن مردانه آستین دار", R.drawable.man3, 1, 20000));
        list.add(new CartItem("پیراهن مردانه آستین دار", R.drawable.man3, 1, 20000));
        adapter.notifyDataSetChanged();
    }
}
