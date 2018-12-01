package com.example.pishgam.onlineshop2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

import com.example.pishgam.onlineshop2.Models.Paid;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.List;
import java.util.Random;

public class LastPurcahsedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context con;
    List<Paid> list;
    LayoutInflater inflater=null;
    int lastPosition=-1;
    Setting setting=null;

    public LastPurcahsedAdapter(Context con, List<Paid> list) {
        this.con = con;
        this.list = list;
        inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setting=new Setting(con);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cart_last_purchase_inflate_layout,parent,false);
        return new PaidView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PaidView viewHolder=(PaidView)holder;
        Paid model=list.get(position);
        viewHolder.img.setImageResource(model.getImg());
        viewHolder.disc.setText(model.getDisc());
        viewHolder.price.setText(model.getPrice()+" "+"تومان");
        viewHolder.counter.setText("تعداد :"+String.valueOf(model.getCount()));
        viewHolder.status.setTextColor(Color.GREEN);
        viewHolder.status.setText(model.getStatus());
        viewHolder.click.setOnClickListener(new View.OnClickListener() {
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
                        setting.restartActivity((Activity)con);
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

        //-------------------------Animate-----------------------
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

    public class PaidView extends RecyclerView.ViewHolder{
        ImageView img;
        TextView disc,price,counter,status;
        RelativeLayout click;
        public PaidView(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.cart_last_img);
            disc=(TextView)itemView.findViewById(R.id.cart_last_disc);
            price=(TextView)itemView.findViewById(R.id.cart_last_price);
            counter=(TextView)itemView.findViewById(R.id.cart_last_counter);
            status=(TextView)itemView.findViewById(R.id.cart_last_status);
            click=(RelativeLayout) itemView.findViewById(R.id.cart_paid_click);
        }
    }
}
