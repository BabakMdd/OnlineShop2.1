package com.example.pishgam.onlineshop2.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PowerManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Adapters.GridAdapter;
import com.example.pishgam.onlineshop2.Adapters.OnLoadMoreListener;
import com.example.pishgam.onlineshop2.Adapters.SearchAdapter;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.Models.SearchProducts;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Search extends AppCompatActivity {

    Setting setting=null;
    ActionBar mAction;
    List<ParentView> list;
    PowerManager.WakeLock wl;
    int progressIndex;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(Search.this);
        setting.setLocale("fa");
        setContentView(R.layout.activity_search);

        //------------------------------------Set Custom Action-------------------------------------
        customAction(getString(R.string.search_activity_title));
        //------------------------------------list--------------------------------------------------
        listInitialize();
        //------------------------------------EditText----------------------------------------------
        EditTextSet();
        //------------------------------------Set Screen On-----------------------------------------
        stayAwake();

    }

    //----------------------------------------Result Initialize-------------------------------------

    private void listInitialize(){
        RecyclerView search=(RecyclerView)findViewById(R.id.search_list);
        search.setHasFixedSize(true);
        GridLayoutManager manager=new GridLayoutManager(Search.this,2,GridLayoutManager.VERTICAL,false);
        search.setLayoutManager(manager);
        list=new ArrayList<>();
        final SearchAdapter adapter=new SearchAdapter(Search.this,search,list);
        if(list.size()>0){
            search.setVisibility(View.VISIBLE);
            search.setAdapter(adapter);
            adapter.setLoadMore(new OnLoadMoreListener() {
                @Override
                public void onLoad() {
                    list.remove(progressIndex);
                    adapter.notifyItemRemoved(progressIndex);
                }
            });
        }

        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation=new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        search.setItemAnimator(animation);


    }

    //--------------------------------Set Search Result---------------------------------

    private void EditTextSet(){
        EditText search=(EditText)findViewById(R.id.searchText);
        String result=search.getText().toString();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    //----------------------------------------Set Display On-------------------------------------------

    private void stayAwake(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Stay Awake");
        wl.acquire();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inner_page_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.home:
                setting=new Setting(Search.this);
                setting.goHome(Main.class);
                break;
            case android.R.id.home:
                if(Main.isFromMain){
                    setting=new Setting(Search.this);
                    setting.goHome(Main.class);
                }
                else{
                    //---------do somthing
                }
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
}
