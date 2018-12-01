package com.example.pishgam.onlineshop2.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pishgam.onlineshop2.Models.MensProduct;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.RoundedImageView;
import com.example.pishgam.onlineshop2.Utilities.Setting;
import java.util.List;
import java.util.Random;

public class MensProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ParentView> list;
    RecyclerView recyclerView;
    LayoutInflater inflater;
    Context con;
    int lastPosition=-1;
    boolean isLoading=false;
    OnLoadMoreListener loadMore;

    public MensProductAdapter(List<ParentView> list, RecyclerView recyclerView, Context con) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.con = con;
        inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //ParentView view=list.get(position);
        if(list.get(position)!=null){
            return 1;
        }else{
            return 2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View view=inflater.inflate(R.layout.mens_product_inflate_layout,parent,false);
            return new MensView(view);
        }
        else{
            View view=inflater.inflate(R.layout.loading_view,parent,false);
            return new LoadingView(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MensView){
            final MensProduct model= (MensProduct) list.get(position);
            MensView menHolder=(MensView)holder;
            menHolder.icon.setImageResource(model.getImg());
            menHolder.disc.setText(model.getDiscription());
            menHolder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Setting setting=new Setting(con);
                    setting.message(model.getDiscription());
                }
            });
            //-------------------animate----------------
            setAnimation(menHolder.itemView,position);
        }else{
            LoadingView holderView=(LoadingView)holder;
            holderView.loading.setIndeterminate(true);
            //-------------------animate----------------
            setAnimation(holderView.itemView,position);
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

    public class MensView extends RecyclerView.ViewHolder{

        RoundedImageView icon;
        TextView disc;
        RelativeLayout click;
        public MensView(View itemView) {
            super(itemView);
            icon=(RoundedImageView)itemView.findViewById(R.id.list_icon);
            disc=(TextView)itemView.findViewById(R.id.mens_product_disc);
            click=(RelativeLayout)itemView.findViewById(R.id.mens_click);
        }
    }
}
