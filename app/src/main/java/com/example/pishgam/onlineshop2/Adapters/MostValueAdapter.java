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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Models.MostValue;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.List;
import java.util.Random;

/**
 * Created by Babak on 9/10/2017.
 */

public class MostValueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ParentView> list;
    Context con;
    LayoutInflater inflate=null;
    int lastPosition=-1;
    RecyclerView recyclerView;
    boolean isLoading=false;
    OnLoadMoreListener loadmore;

    public MostValueAdapter(Context con, RecyclerView recyclerView,List<ParentView> list) {
        this.con = con;
        this.recyclerView = recyclerView;
        this.list=list;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayoutManager manager=(LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total=manager.getItemCount();
                int lastSeen=manager.findLastVisibleItemPosition();

                if(isLoading==false && lastSeen==total-1){
                    if(loadmore!=null){
                        loadmore.onLoad();
                    }
                    isLoading=true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ParentView.VIEW){
            View view=inflate.inflate(R.layout.value_inflate_items,parent,false);
            return new MostValueView(view);
        }
        else{
            View view=inflate.inflate(R.layout.loading_view,parent,false);
            return new LoadingView(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ParentView current=list.get(position);
        return current.getCurrentType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MostValueView){
            MostValueView value=(MostValueView)holder;
            MostValue model= (MostValue) list.get(position);
            value.img.setImageResource(model.getImg());
            value.disc.setText(model.getDisc());
            value.price.setText(model.getPrice());
            value.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Setting setting=new Setting(con);
                    setting.message("mostValue Clicked");
                }
            });
            //--------------------animate------------------
            setAnimation(value.itemView,position);

        }else{
            LoadingView loading=(LoadingView)holder;
            loading.progress.setIndeterminate(true);
            //--------------------animate------------------
            setAnimation(loading.itemView,position);
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

    //------------------------------------------Setter For LoadMore and Loading State---------------------------------

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setLoadmore(OnLoadMoreListener loadmore) {
        this.loadmore = loadmore;
    }

    public  class LoadingView extends RecyclerView.ViewHolder{

        ProgressBar progress;
        public LoadingView(View itemView) {
            super(itemView);
            progress=(ProgressBar)itemView.findViewById(R.id.loading);
        }
    }


    public class MostValueView extends RecyclerView.ViewHolder{

        ImageView img;
        TextView disc;
        TextView price;
        RelativeLayout click;
        public MostValueView(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.product_img);
            disc=(TextView)itemView.findViewById(R.id.product_disc);
            price=(TextView)itemView.findViewById(R.id.price_value);
            click=(RelativeLayout)itemView.findViewById(R.id.mostValueClick);
        }
    }
}
