package com.example.pishgam.onlineshop2.Adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.Models.SearchProducts;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.List;
import java.util.Random;

/**
 * Created by Babak on 9/13/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context con;
    RecyclerView recyclerView;
    List<ParentView> list;
    int lastPosition=-1;
    LayoutInflater inflate=null;
    boolean isLoading=false;
    OnLoadMoreListener loadMore;

    public SearchAdapter(Context con,RecyclerView recyclerView,List<ParentView> list){
        this.con=con;
        this.recyclerView=recyclerView;
        this.list=list;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final GridLayoutManager manager=(GridLayoutManager)recyclerView.getLayoutManager();
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

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setLoadMore(OnLoadMoreListener loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public int getItemViewType(int position) {
        ParentView parent=list.get(position);
        return parent.getCurrentType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ParentView.VIEW){
            View view=inflate.inflate(R.layout.search_inflate_layout,parent,false);
            return new SearchView(view);
        }else{
            View view=inflate.inflate(R.layout.loading_view,parent,false);
            return new LoadingView(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SearchView){
            SearchProducts model= (SearchProducts) list.get(position);
            SearchView viewHolder=(SearchView)holder;
            viewHolder.img.setImageResource(model.getImg());
            viewHolder.disc.setText(model.getTitle());
            viewHolder.price.setText(model.getPrice());
            viewHolder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Setting setting=new Setting(con);
                    setting.message("search clicked");
                }
            });

            //-------------------animate------------------
            setAnimation(viewHolder.itemView,position);
        }else{
            LoadingView loadHolder=(LoadingView)holder;
            loadHolder.loading.setIndeterminate(true);
            //-------------------animate-------------------
            setAnimation(loadHolder.itemView,position);
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

    public class SearchView extends RecyclerView.ViewHolder{

        ImageView img;
        TextView disc,price;
        RelativeLayout click;
        public SearchView(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.product_img_search);
            disc=(TextView)itemView.findViewById(R.id.disc_search);
            price=(TextView)itemView.findViewById(R.id.price_search);
            click=(RelativeLayout)itemView.findViewById(R.id.search_click);
        }
    }
}
