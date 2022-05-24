package com.liqi.gamejniread;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private DisplayMetrics displayMetrics;
    private int width,high;
    private String ss = "sss";
    public MyService() {
        System.identityHashCode(ss);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void initWindow(){

    if (Settings.canDrawOverlays(this)){
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        //获取屏幕尺寸

        displayMetrics = new  DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;
        high = displayMetrics.heightPixels;

        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        layoutParams.format= PixelFormat.RGB_888; //设置背景透明度
        layoutParams.width = 512; //设置悬浮窗的长度值 单位是px不是dp
        layoutParams.height = 450;
        layoutParams.gravity= Gravity.LEFT;
        layoutParams.x = 100;
        layoutParams.y = 100;

        //加载悬浮窗布局
        View view = LayoutInflater.from(this).inflate(R.layout.xuanfuchuanxml,null);
        windowManager.addView(view,layoutParams);











    }





    }
}