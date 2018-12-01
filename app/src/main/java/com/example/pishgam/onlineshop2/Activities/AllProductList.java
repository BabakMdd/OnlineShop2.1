package com.example.pishgam.onlineshop2.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PowerManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Adapters.GridAdapter;
import com.example.pishgam.onlineshop2.Models.Categories;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AllProductList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ActionBar mAction;
    Setting setting=null;
    PowerManager.WakeLock wl;
    List<Categories> list;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(AllProductList.this);
        setting.setLocale("fa");
        setContentView(R.layout.activity_all_product_list);

        //--------------------------------------------Custom ActionBar------------------------------
        customAction(getString(R.string.activity_all_product));
        //--------------------------------------------Set Display On--------------------------------
        stayAwake();
        //--------------------------------------------Call Gridview---------------------------------
        setGrid();
    }

    //----------------------------------------------Grid init---------------------------------------
    private void setGrid(){
        GridView grid=(GridView)findViewById(R.id.Grid_Items);
        grid.setVerticalSpacing(10);
        //-----------------------------Grid Animation---------------------------
        Animation animation = AnimationUtils.loadAnimation(AllProductList.this,R.anim.grid_animation_display);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
        grid.setLayoutAnimation(controller);
        //------------------------------Set Adapter For Grid--------------------
        list=new ArrayList<>();
        list.add(new Categories(getString(R.string.mens),R.drawable.man1));
        list.add(new Categories(getString(R.string.womens),R.drawable.women1));
        list.add(new Categories(getString(R.string.childs),R.drawable.girl2));
        list.add(new Categories(getString(R.string.sport),R.drawable.boy1));

        GridAdapter adapter=new GridAdapter(AllProductList.this,list);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
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

    private void stayAwake(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Stay Awake");
        wl.acquire();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case R.id.home:
                setting=new Setting(AllProductList.this);
                setting.goHome(Main.class);
                break;
            case android.R.id.home:
                setting=new Setting(AllProductList.this);
                setting.goHome(Main.class);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inner_page_menu,menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wl.release();
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        setting=new Setting(AllProductList.this);
        if(i==0){
           setting.openActivity(MensProducts.class,view);
        }
    }
}
