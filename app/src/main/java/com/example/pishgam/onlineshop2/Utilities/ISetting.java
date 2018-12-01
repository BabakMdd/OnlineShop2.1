package com.example.pishgam.onlineshop2.Utilities;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import java.util.Locale;

/**
 * Created by Babak on 9/8/2017.
 */

public interface ISetting {

    public void openActivity(Class<? extends Activity> activity,View view);

    public void goHome(Class<? extends Activity> home);

    public void snackMessage(CoordinatorLayout coordinate,String msg);

    public void snackError(CoordinatorLayout coordinate,String error,String action);

    public void message(String msg);

    public void setLocale(String locale);

    public void setLocale(Locale locale);

    public void restartActivity(Activity act);
}
