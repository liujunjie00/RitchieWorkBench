package com.liqi.test3399.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


public class UIYtils {
    private static UIYtils instance;
    private static float WIDTH = 0f;//参考屏幕的宽度
    private static float HEIGHT = 0f;//参考屏幕的高度
    public static final float STANDARF_WIDTH=1080f;
    public static final float STANDARF_HEIGHT=1920f;
    public static float appDensity;  //表示屏幕的密度
    public static float appScaleDensity; //表示字体缩放比例

    public UIYtils() {
    }

    public static void setDensity(Application application, Activity activity){
        Context context = application.getApplicationContext();
        WindowManager windowManager = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        Display display = windowManager.getDefaultDisplay();
        int ppi = 450;
        if (display.getHeight()== 1024){
            ppi = 280;
        }
        if (display.getHeight() == 1036){
            ppi = 200;
        }

        // 1024 *1920 300
        // 1080 * 1920 450
        //1036* 2310

        WIDTH = display.getHeight()-ppi;
        HEIGHT = display.getWidth();

        Log.d("liujunjie",display.getHeight()+"屏幕宽度"+display.getWidth());
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();

        if (appDensity == 0){

            appDensity = displayMetrics.density;
            appScaleDensity = displayMetrics.scaledDensity;

        }

        float targetDensity = displayMetrics.widthPixels/WIDTH;
        float targetScaleDensity = targetDensity *(appScaleDensity/appDensity);
        int targetDensityDpi = (int) (targetDensity*160);

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        dm.density = targetDensity;
        dm.scaledDensity = targetScaleDensity;
        dm.densityDpi = targetDensityDpi;
    }


}
