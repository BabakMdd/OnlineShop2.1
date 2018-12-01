package com.example.pishgam.onlineshop2.Utilities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pishgam.onlineshop2.R;

import java.util.Locale;

/**
 * Created by Babak on 9/8/2017.
 */

public class Setting implements ISetting {

    Context context;
    public Setting(Context context){
        this.context=context;
    }

    //---------------------------------Open Activity and Go home Handeling------------------------------

    @Override
    public void openActivity(Class<? extends Activity> activity,View view) {
        Intent intent=new Intent(context,activity);
        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                0, view.getWidth(), view.getHeight());
        context.startActivity(intent,options.toBundle());
    }

    @Override
    public void goHome(Class<? extends Activity> home) {
        Intent parentActivityIntent = new Intent(context, home);
        parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(parentActivityIntent);
    }

    @Override
    public void snackMessage(CoordinatorLayout coordinate, String msg) {
        Snackbar snack=Snackbar.make(coordinate,msg,Snackbar.LENGTH_LONG);
        snack.show();
    }

    @Override
    public void snackError(CoordinatorLayout coordinate, String error,String action) {
        final Snackbar snack=Snackbar.make(coordinate,error,Snackbar.LENGTH_INDEFINITE);
        snack.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snack.dismiss();
            }
        });
        snack.show();
    }

    @Override
    public void message(String msg) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(R.layout.custom_toast, null);
        TextView title=(TextView)toastRoot.findViewById(R.id.msg);
        title.setText(msg);
        Toast toast = new Toast(context);
        toast.setView(toastRoot);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void setLocale(String locale) {
        setLocale(new Locale(locale));
    }

    @Override
    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }

    @Override
    public void restartActivity(Activity act) {
        Intent intent=new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();
    }
}
