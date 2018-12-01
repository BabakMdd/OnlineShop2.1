package com.example.pishgam.onlineshop2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Activities.ShopingCart;
import com.example.pishgam.onlineshop2.Models.CartItem;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.List;
import java.util.Random;

/**
 * Created by Babak on 9/18/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CartItem> list;
    Context con;
    LayoutInflater inflate=null;
    public static int i=1;
    Setting setting=null;
    int lastPosition=-1;

    public CartAdapter(List<CartItem> list, Context con) {
        this.list = list;
        this.con = con;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setting=new Setting(con);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflate.inflate(R.layout.cart_inflate_list,parent,false);
        return new CartView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CartItem model= (CartItem) list.get(position);
        final CartView viewHolder=(CartView)holder;
        viewHolder.img.setImageResource(model.getImg());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert=new AlertDialog.Builder(con);
                alert.setTitle(R.string.cart_delete);
                alert.setIcon(R.mipmap.ic_launcher);
                alert.setMessage(R.string.cart_delete_msg);
                alert.setPositiveButton(R.string.cart_delete_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        setting.restartActivity((Activity) con);
                    }
                });
                alert.setNegativeButton(R.string.cart_delete_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog;
                dialog=alert.create();
                dialog.show();
            }
        });
        viewHolder.disc.setText(model.getDisc());
        viewHolder.price.setText(String.valueOf(model.getPrice())+" "+"تومان");
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                viewHolder.counter.setText(String.valueOf(i));
                viewHolder.price.setText(String.valueOf(model.getPrice()*i)+" "+"تومان");
            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i!=1){
                    i--;
                    viewHolder.counter.setText(String.valueOf(i));
                    viewHolder.price.setText(String.valueOf(model.getPrice()*i)+" "+"تومان");
                }
            }
        });
        viewHolder.counter.setText(String.valueOf(model.getCount()));
        //--------------------animate----------------------------
        setAnimation(viewHolder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    public class CartView extends RecyclerView.ViewHolder{

        Button delete;
        ImageView img;
        TextView disc,price,counter;
        ImageButton plus,minus;
        RelativeLayout click;
        public CartView(View itemView) {
            super(itemView);
            delete=(Button)itemView.findViewById(R.id.cart_delete);
            img=(ImageView)itemView.findViewById(R.id.img_cart);
            disc=(TextView)itemView.findViewById(R.id.cart_disc);
            price=(TextView)itemView.findViewById(R.id.cart_price);
            counter=(TextView)itemView.findViewById(R.id.counter);
            plus=(ImageButton)itemView.findViewById(R.id.cart_counter_plus);
            minus=(ImageButton)itemView.findViewById(R.id.cart_counter_minus);
            click=(RelativeLayout)itemView.findViewById(R.id.cart_click);

        }
    }
}
