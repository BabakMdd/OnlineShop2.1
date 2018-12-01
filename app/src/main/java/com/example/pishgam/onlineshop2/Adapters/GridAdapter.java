package com.example.pishgam.onlineshop2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Models.Categories;
import com.example.pishgam.onlineshop2.R;

import java.util.List;

/**
 * Created by Babak on 9/13/2017.
 */

public class GridAdapter extends ArrayAdapter<Categories> {

    Context con;
    List<Categories> list;
    LayoutInflater inflate;

    public GridAdapter(Context con,List<Categories> list){
        super(con, R.layout.grid_inflate_layout,list);
        this.con=con;
        this.list=list;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflate.inflate(R.layout.grid_inflate_layout,parent,false);
        ViewHolder holder=new ViewHolder();
        holder.img=(ImageView)convertView.findViewById(R.id.grid_img);
        holder.txt=(TextView)convertView.findViewById(R.id.grid_disc);
        Categories categories=list.get(position);
        holder.img.setImageResource(categories.getImage());
        holder.txt.setText(categories.getTitle());

        return convertView;
    }

    private class ViewHolder {
        ImageView img;
        TextView txt;
    }
}
