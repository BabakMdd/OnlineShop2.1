package com.example.pishgam.onlineshop2.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pishgam.onlineshop2.Adapters.CartAdapter;
import com.example.pishgam.onlineshop2.Adapters.ShoppingPagerAdapter;
import com.example.pishgam.onlineshop2.Models.CartItem;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShopingCart extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    List<CartItem> list;
    Setting setting = null;
    RecyclerView cart;
    LinearLayout pay;
    CartAdapter adapter;
    ActionBar mAction;
    PowerManager.WakeLock wl;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting = new Setting(ShopingCart.this);
        setting.setLocale("fa");
        setContentView(R.layout.activity_shoping_cart);

        //-------------------------------------------CustomAction------------------------------
        customAction(getString(R.string.cart_shop));

        //-------------------------------------------set Display On----------------------------
        stayAwake();

        //-------------------------------------------Tabs Setting---------------------------------
        TabsLayout();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inner_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setting = new Setting(ShopingCart.this);
        switch (id) {
            case R.id.home:
                setting.goHome(Main.class);
                break;
            case android.R.id.home:
                setting.goHome(Main.class);
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

    //----------------------------------------Config Tabs-------------------------------------------

    private void TabsLayout(){
        //Initializing the tablayout
        tabLayout = (TabLayout)findViewById(R.id.tab);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.old_shopping)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.shopping_cart)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        ShoppingPagerAdapter adapter = new ShoppingPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),ShopingCart.this);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        customTabText();
    }

    //-------------------------------------Customize Tab Text and Color-----------------------------------
    private void customTabText(){
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            View tv=(View) LayoutInflater.from(this).inflate(R.layout.custom_tab_text,null);
            TextView title=(TextView)tv.findViewById(R.id.tabtext);
            Typeface typeface=Typeface.createFromAsset(getResources().getAssets(),"Fonts/Sans.ttf");
            title.setTextColor(Color.WHITE);
            title.setTextSize(9);
            if (i==0){
                title.setText(getString(R.string.old_shopping));
            }
            else{
                title.setText(getString(R.string.shopping_cart));
            }
            title.setTypeface(typeface);

            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    //----------------------------------------Set Display On-------------------------------------------

    private void stayAwake() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Stay Awake");
        wl.acquire();
    }

    //----------------------------------------action bar setting-----------------------------------
    public void customAction(String page) {
        mAction = getDelegate().getSupportActionBar();
        mAction.setDisplayShowHomeEnabled(false);
        mAction.setDisplayShowTitleEnabled(false);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup view = (ViewGroup) inflate.inflate(R.layout.custom_action_bar, null);
        TextView title = (TextView) view.findViewById(R.id.page_title);
        title.setText(page);
        mAction.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673AB7")));
        mAction.setHomeButtonEnabled(true);
        mAction.setDisplayHomeAsUpEnabled(true);
        // mAction.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        mAction.setDisplayShowCustomEnabled(true);
        mAction.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        // mAction.setTitle(page);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    ///----------------------------------------Tab Click Listener------------------------------------
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
