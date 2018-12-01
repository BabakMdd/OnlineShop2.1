package com.example.pishgam.onlineshop2.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.pishgam.onlineshop2.Models.Brands;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Babak on 9/10/2017.
 */

public class BrandsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ParentView> list;
    Context con;
    LayoutInflater inflate=null;
    boolean isLoading=false;
    OnLoadMoreListener loadMore;
    RecyclerView recyclerView;
    int lastPosition=-1;

    public BrandsAdapter(List<ParentView> list, Context con, RecyclerView recyclerView) {
        this.list = list;
        this.con = con;
        this.recyclerView = recyclerView;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayoutManager manager=(LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int total=manager.getItemCount();
                int lastSeen=manager.findLastVisibleItemPosition();
                if(isLoading==false && lastSeen==total-1){
                    if(loadMore!=null){
                        loadMore.onLoad();
                    }
                    isLoading=true;
                }
            }
        });
    }


    public void setLoadMore(OnLoadMoreListener loadMore) {
        this.loadMore = loadMore;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public int getItemViewType(int position) {
        ParentView parent=list.get(position);
        return parent.getCurrentType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ParentView.VIEW){
            View view=inflate.inflate(R.layout.brands_inflate_items,parent,false);
            return new BrandsView(view);
        }
        else{
            View view=inflate.inflate(R.layout.loading_view,parent,false);
            return new LoadingView(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BrandsView){
            BrandsView brandHolder=(BrandsView)holder;
            Brands model= (Brands) list.get(position);
            brandHolder.logo.setImageResource(model.getImg());
            //------------------------animation---------------------------
            setAnimation(brandHolder.itemView,position);
        }
        else{
            LoadingView loadingHolder=(LoadingView)holder;
            loadingHolder.loading.setIndeterminate(true);
            //------------------------animation---------------------------
            setAnimation(loadingHolder.itemView,position);
        }
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


    public class LoadingView extends RecyclerView.ViewHolder{
        ProgressBar loading;
        public LoadingView(View itemView) {
            super(itemView);
            loading=(ProgressBar)itemView.findViewById(R.id.loading);
        }
    }

    public class BrandsView extends RecyclerView.ViewHolder{

        ImageView logo;
        public BrandsView(View itemView) {
            super(itemView);
            logo=(ImageView)itemView.findViewById(R.id.img_brands);
        }
    }
}
