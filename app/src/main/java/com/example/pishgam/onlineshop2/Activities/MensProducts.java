package com.example.pishgam.onlineshop2.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PowerManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Adapters.MensProductAdapter;
import com.example.pishgam.onlineshop2.Adapters.OnLoadMoreListener;
import com.example.pishgam.onlineshop2.Models.MensProduct;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MensProducts extends AppCompatActivity {

    List<ParentView> list;
    Setting setting=null;
    PowerManager.WakeLock wl;
    ActionBar mAction;
    MensProductAdapter adapter;
    int progressIndex;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(MensProducts.this);
        setting.setLocale("fa");
        setContentView(R.layout.activity_mens_products);

        //------------------------------------Custom ActionBar--------------------------------
        customAction(getString(R.string.mens));

        //------------------------------------Set Display On-----------------------------------
        stayAwake();

        //-------------------------------------initialize List---------------------------------
        listInit();
    }

    private void listInit(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.mens_products);
        LinearLayoutManager manager=new LinearLayoutManager(MensProducts.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        list=new ArrayList<>();
        adapter=new MensProductAdapter(list,recyclerView,MensProducts.this);
        adapter.setLoadMore(new OnLoadMoreListener() {
            @Override
            public void onLoad() {
                list.add(null);
                progressIndex=list.size()-1;
                adapter.notifyItemInserted(progressIndex);
                addNew();
            }
        });

        recyclerView.setAdapter(adapter);

        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation=new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animation);

        addItem();
    }

    private void addItem(){
        list.add(new MensProduct(R.drawable.bolooz,"بلوز مردانه"));
        list.add(new MensProduct(R.drawable.shalvar,"شلوار مردانه"));
        list.add(new MensProduct(R.drawable.pirahan,"پیراهن مردانه"));
        list.add(new MensProduct(R.drawable.majlesi,"لباس مجلسی مردانه"));
        list.add(new MensProduct(R.drawable.soishirt,"لباس پاییزه مردانه"));
        list.add(new MensProduct(R.drawable.jaket,"ژاکت های مردانه"));
        adapter.notifyDataSetChanged();
    }

    private void addNew(){
        list.remove(progressIndex);
        adapter.notifyItemRemoved(progressIndex);
        list.add(new MensProduct(R.drawable.bolooz,"بلوز مردانه"));
        list.add(new MensProduct(R.drawable.shalvar,"شلوار مردانه"));
        list.add(new MensProduct(R.drawable.pirahan,"پیراهن مردانه"));
        list.add(new MensProduct(R.drawable.majlesi,"لباس مجلسی مردانه"));
        list.add(new MensProduct(R.drawable.soishirt,"لباس پاییزه مردانه"));
        list.add(new MensProduct(R.drawable.jaket,"ژاکت های مردانه"));
        adapter.setLoading(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inner_page_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        setting=new Setting(MensProducts.this);
        switch (id){
            case R.id.home:
             setting.goHome(Main.class);
                break;
            case android.R.id.home:
                setting.openActivity(AllProductList.class,new View(MensProducts.this));
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wl.release();
        finish();
    }

    //----------------------------------------action bar setting-----------------------------------
    public void customAction(String page){
        mAction=getDelegate().getSupportActionBar();
        mAction.setDisplayShowHomeEnabled(false);
        mAction.setDisplayShowTitleEnabled(false);
        LayoutInflater inflate=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup view=(ViewGroup)inflate.inflate(R.layout.custom_action_bar,null);
        TextView title=(TextView)view.findViewById(R.id.page_title);
        title.setText(page);
        mAction.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673AB7")));
        mAction.setHomeButtonEnabled(true);
        mAction.setDisplayHomeAsUpEnabled(true);
        // mAction.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        mAction.setDisplayShowCustomEnabled(true);
        mAction.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP );
        // mAction.setTitle(page);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //------------------------------------------Stay Awake------------------------------------------
    private void stayAwake(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Stay Awake");
        wl.acquire();
    }
}
