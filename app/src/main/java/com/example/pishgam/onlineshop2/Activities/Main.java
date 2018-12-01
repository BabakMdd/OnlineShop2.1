package com.example.pishgam.onlineshop2.Activities;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.pishgam.onlineshop2.Adapters.BrandsAdapter;
import com.example.pishgam.onlineshop2.Adapters.MostValueAdapter;
import com.example.pishgam.onlineshop2.Adapters.NewProductAdapter;
import com.example.pishgam.onlineshop2.Adapters.OnLoadMoreListener;
import com.example.pishgam.onlineshop2.Models.Brands;
import com.example.pishgam.onlineshop2.Models.MostValue;
import com.example.pishgam.onlineshop2.Models.NewProducts;
import com.example.pishgam.onlineshop2.Models.ParentView;
import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.BadgeView;
import com.example.pishgam.onlineshop2.Utilities.Setting;
import com.example.pishgam.onlineshop2.Utilities.ViewFlipperIndicator;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , GestureDetector.OnGestureListener {

    Setting setting=null;
    android.support.v7.app.ActionBar mAction;
    List<ParentView> list,NewProductlist,logos;
    NewProductAdapter newAdapter;
    MostValueAdapter adapter;
    BrandsAdapter logoAdapter;
    boolean doubleBackToExitPressedOnce=false;
    PowerManager.WakeLock wl;

    //-----------------------------redirect from Main---------------------

    public static boolean isFromMain=false;

    //-----------------------------For FlipperView--------------------
    ViewFlipperIndicator slideshow;
    GestureDetector detector=null;
    Handler myhandler=new Handler();
    private Animation lInAnim;
    private Animation lOutAnim;
    // flipper restarts flipping
    private Runnable flipController = new Runnable() {
        @Override
        public void run() {
            slideshow.setInAnimation(lInAnim);
            slideshow.setOutAnimation(lOutAnim);
            slideshow.showNext();
            slideshow.startFlipping();
        }
    };

    //-----------------------------ProgressBar----------------------
    int progressIndex,progressIndexNew,progressIndexLogo;


    //-----------------------------Change Entire Program Default Font-----------------------
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(Main.this);
        setting.setLocale("fa");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //----------------------------------------------------Set Drawer Toggle ---------------------------------

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Main.this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //-----------------------------------------------------Set Screen On-------------------------------------
        stayAwake();
        //-----------------------------------------------------Set Slidshow Start--------------------------------
        setSlide(savedInstanceState);
        //-----------------------------------------------------Snack Connection Error-----------------------------
        CoordinatorLayout coor=(CoordinatorLayout)findViewById(R.id.coordinate);
        setting.snackError(coor,"ارتباط با سرور برقرار نیست","تلاش مجدد");
        //------------------------------------------------------RecyclerView MostValue initialize------------------
        listInit();
        //------------------------------------------------------RecyclerView NewProducts initialize----------------
        setNewProductList();
        //------------------------------------------------------RecycleView Logo initialize------------------------
        logosList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //-------------------------------------exit app on 2 click back button---------------------
            if(doubleBackToExitPressedOnce){
                super.onBackPressed();
                wl.release();
                moveTaskToBack(true);
                System.exit(1);
            }
            this.doubleBackToExitPressedOnce = true;
            setting=new Setting(Main.this);
            setting.message(getString(R.string.taptoexit));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    //------------------------------------------------Set Badge For Shopping Cart----------------------

    private static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeView badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeView) {
            badge = (BadgeView) reuse;
        } else {
            badge = new BadgeView(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    //-------------------------------------------Badge Configuration--------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=(MenuItem)menu.findItem(R.id.shopping_cart);
        LayerDrawable view=(LayerDrawable)item.getIcon();
        setBadgeCount(Main.this,view,"2");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.shopping_cart:
                setting=new Setting(Main.this);
                setting.openActivity(ShopingCart.class,new View(Main.this));
                break;
            case R.id.search:
                isFromMain=true;
                setting=new Setting(Main.this);
                setting.openActivity(Search.class,new View(Main.this));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //----------------------------------------Set Display On-------------------------------------------

    private void stayAwake(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Stay Awake");
        wl.acquire();
    }


    //-----------------------------------------All Product Button Click---------------------------------

    public void allProducts(View view){
        setting.openActivity(AllProductList.class,view);
    }



    //------------------------------------------Set RecyclerView Initialized----------------------------

    private void listInit(){
        RecyclerView mostValue=(RecyclerView)findViewById(R.id.most_value_list);
        Button btn=(Button)findViewById(R.id.more_btn_value);
        list=new ArrayList<>();

        LinearLayoutManager manager=new LinearLayoutManager(Main.this,LinearLayoutManager.HORIZONTAL,false);
        mostValue.setHasFixedSize(true);
        mostValue.setLayoutManager(manager);
        adapter=new MostValueAdapter(Main.this,mostValue,list);
        adapter.setLoadmore(new OnLoadMoreListener() {
            @Override
            public void onLoad() {

                //-------------------------------------View Loading Progress-----------------------
                list.add(null);
                progressIndex=list.size()-1;
                adapter.notifyItemInserted(progressIndex);
                addItem();
            }
        });

        mostValue.setAdapter(adapter);
        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation=new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        mostValue.setItemAnimator(animation);

        addNewItem();
    }

    private void setNewProductList(){
        RecyclerView newProduct=(RecyclerView)findViewById(R.id.most_new_list);
        Button morebtn=(Button)findViewById(R.id.more_btn_new);
        NewProductlist=new ArrayList<>();
        newProduct.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(Main.this,LinearLayoutManager.HORIZONTAL,false);
        newProduct.setLayoutManager(manager);
        newAdapter = new NewProductAdapter(NewProductlist,Main.this,newProduct);
        adapter.setLoadmore(new OnLoadMoreListener() {
            @Override
            public void onLoad() {
                //-------------------------------------View Loading Progress-----------------------
                NewProductlist.add(null);
                progressIndexNew=NewProductlist.size()-1;
                newAdapter.notifyItemInserted(progressIndexNew);
                addAfterLoading();
            }
        });

        newProduct.setAdapter(newAdapter);

        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation=new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        newProduct.setItemAnimator(animation);

        addNewProduct();
    }


    //----------------------------------------Set Logo list init------------------------------

    private void logosList(){
        RecyclerView logoList=(RecyclerView)findViewById(R.id.brands_list);
        Button btn=(Button)findViewById(R.id.more_btn_brands);
        LinearLayoutManager manager=new LinearLayoutManager(Main.this,LinearLayoutManager.HORIZONTAL,false);
        logoList.setHasFixedSize(true);
        logoList.setLayoutManager(manager);
        logos=new ArrayList<>();
        logoAdapter=new BrandsAdapter(logos,Main.this,logoList);
        adapter.setLoadmore(new OnLoadMoreListener() {
            @Override
            public void onLoad() {
                logos.add(null);
                progressIndexLogo=logos.size()-1;
                logoAdapter.notifyItemInserted(progressIndexLogo);
                addNewLogo();
            }
        });

        logoList.setAdapter(logoAdapter);

        //---------------------------Animation For Recycle-----------------------------
        RecyclerView.ItemAnimator animation=new DefaultItemAnimator();
        animation.setAddDuration(1000);
        animation.setRemoveDuration(1000);
        logoList.setItemAnimator(animation);
        addLogo();
    }

    //----------------------------------------Sel Brands Image -------------------------

    private void addLogo(){
        logos.add(new Brands(R.drawable.levis));
        logos.add(new Brands(R.drawable.adidas));
        logos.add(new Brands(R.drawable.chanel));
        logos.add(new Brands(R.drawable.dior));
        logos.add(new Brands(R.drawable.gucci));
        logoAdapter.notifyDataSetChanged();
    }

    private void addNewLogo(){

        logos.add(null);
        logoAdapter.notifyItemRemoved(progressIndexLogo);
        logos.add(new Brands(R.drawable.boss));
        logos.add(new Brands(R.drawable.lacoste));
        logos.add(new Brands(R.drawable.theory));
        logos.add(new Brands(R.drawable.zara));

        logoAdapter.setLoading(false);
        logoAdapter.notifyDataSetChanged();

    }

    //----------------------------------------Add Most Value Clothes---------------------------------------

    private void addItem(){

        list.remove(progressIndex);
        adapter.notifyItemRemoved(progressIndex);
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        //--------------------------------------set Loading to false for Continue Fetch Data---------------------
        adapter.setLoading(false);
        adapter.notifyDataSetChanged();
    }


    private void addNewItem(){
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        list.add(new MostValue(R.drawable.boy1,"12000 تومان","شلوار کتان پسرانه"));
        adapter.notifyDataSetChanged();
    }

    //----------------------------------------Add New Product To list--------------------------------------------

    private void addNewProduct(){
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        newAdapter.notifyDataSetChanged();
    }


    private void addAfterLoading(){
        NewProductlist.remove(progressIndexNew);
        newAdapter.notifyItemRemoved(progressIndexNew);
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));
        NewProductlist.add(new NewProducts(R.drawable.women1,"20000 تومان","لباس شب زنانه"));

        newAdapter.setLoading(false);
        newAdapter.notifyDataSetChanged();
    }
    //------------------------------------------Initial SlideShow Items---------------------------------

    private void setSlide( Bundle savedInstanceState){
        slideshow=(ViewFlipperIndicator)findViewById(R.id.Slideshow);
        slideshow.setRadius(10);
        slideshow.setMargin(10);

        // set colors for the indicators
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.white));
        slideshow.setPaintCurrent(paint);


        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        slideshow.setPaintNormal(paint);

        slideshow.setAutoStart(true);

        // flipper has a previous position. we should restore it
        if (savedInstanceState != null) {
            slideshow.setDisplayedChild(savedInstanceState.getInt("flipper_position"));
        }

        slideshow.startFlipping();

        detector=new GestureDetector(Main.this,this);

    }




    //-----------------------------------------Some methods must be overriden because of FlipperView -------------


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("flipper_position",slideshow.getDisplayedChild());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {}

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        // you can change this value
        float sensitivity = 50;

        if ((motionEvent.getX() - motionEvent1.getX()) > sensitivity) {

            slideshow.showNext();

            // first stops flipping
            slideshow.stopFlipping();

            // then flipper restarts flipping in 3 seconds
            myhandler.postDelayed(flipController, 3000);

            return true;
        } else if ((motionEvent1.getX() - motionEvent.getX()) > sensitivity) {
            Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
            Animation rOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
            slideshow.setInAnimation(rInAnim);
            slideshow.setOutAnimation(rOutAnim);
            slideshow.showPrevious();

            // first stops flipping
            slideshow.stopFlipping();

            // then flipper restarts flipping in 3 seconds
            myhandler.postDelayed(flipController, 3000);

            return true;
        }

        return true;
    }
}
