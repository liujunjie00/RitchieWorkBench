package com.ritchie.readandwrite;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.lights.LightsManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Readandwrite extends Service implements View.OnClickListener{

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private DisplayMetrics displayMetrics;
    private int width,high;
    private String ss = "sss";
    private View view;
    private Button button,button2,button3,button4;
    private EditText editText,editText2,editText3,editText4;
    private TextView textView;
    private static String TAG = "liujunjie";
    private static String TAG1 = "liujunjie1";
    private String searcherValue;
    private String modifyValue;
    private View layout;
    private boolean in = false;
    public Readandwrite() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("liujunjie","服务被创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initWindow();
        button.setOnClickListener(this::onClick);
        editText.setOnClickListener(this::onClick);
        textView.setOnClickListener(this::onClick);
        button2.setOnClickListener(this::onClick);
        button3.setOnClickListener(this::onClick);
        layout.setOnClickListener(this::onClick);
        return super.onStartCommand(intent, flags, startId);

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
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            layoutParams.format= PixelFormat.RGB_565;
            layoutParams.alpha = 0.8f;  //设置背景透明度
            layoutParams.width = 400; //设置悬浮窗的长度值 单位是px不是dp
            layoutParams.height = 300;
            layoutParams.gravity= Gravity.LEFT;
            layoutParams.x = 50;
            layoutParams.y = 100;
            view = LayoutInflater.from(this).inflate(R.layout.floating_window_layout,null);
            button = view.findViewById(R.id.button1);
            editText = view.findViewById(R.id.editText);
            editText2 = view.findViewById(R.id.editText2);
            textView = view.findViewById(R.id.textview1);
            button4 = view.findViewById(R.id.button4);
            editText3 = view.findViewById(R.id.editText3);
            editText4 = view.findViewById(R.id.editText4);
            button2 = view.findViewById(R.id.button2);
            button3 = view.findViewById(R.id.button3);
            layout = view.findViewById(R.id.layout1);

            windowManager.addView(view,layoutParams);

        }


    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button)
        switch (v.getId()){
            case R.id.button1:
                Log.d(TAG, "onClick: 按钮被点击");
                buttonEvent();
                break;
            case R.id.editText:
                Log.d(TAG, "onClick: 文本输入框被点击");
                break;

            case R.id.textview1:
                Log.d(TAG, "onClick: textview被点击");
                break;
            case R.id.button2:
                Log.d(TAG,"Button2被点击");
                button2Event();
            case R.id.button4:
                button4Event();
        }
    }

    private void button4Event() {
        int pid = getPidKK("com.ezl.emulator.nes");
        List<String[]> list = getStartAndEnd(pid);

    }

    private void button2Event() {
        String starAdddr = editText3.getText().toString().trim();
        if (starAdddr.length()<1){
            textView.setText("请输入字符");
            return;
        }
        String endAddr = editText4.getText().toString().trim();

        // 执行查找maps的操作 接受返回值
        int pid = getPidKK("com.ezl.emulator.nes");
        StringBuffer result = SystemUtil.execShellCmd2("su root /system/bin/a.out "+pid+" "+starAdddr+" "+endAddr+" "+ 25);
        Log.d(TAG1, "a.out返回值: "+result.toString());



    }

    // 相应button1的点击事件
    private void buttonEvent() {

        searcherValue= editText.getText().toString().trim();
        if (searcherValue.length()<1){
            textView.setText("请输入字符");
           // return;
        }
        modifyValue = editText2.getText().toString().trim();

        // 执行查找maps的操作 接受返回值
        int pid = getPidKK("com.ezl.emulator.nes");

        StringBuffer stringBuffer = new StringBuffer();
        List<String[]> list = getStartAndEnd(pid);

        //循环 调用c执行文件查找地址 接受返回值
        for (int i = 0; i <list.size() ; i++) {
            StringBuffer result = SystemUtil.execShellCmd2("su root /system/bin/b.out "+pid+" "+list.get(i)[0]+" "+list.get(i)[1]+" "+searcherValue+" "+modifyValue);

                Log.d(TAG1, "查找全部的返回值: "+result.toString());


        }


    }

    public int getPidKK(String appName){
        int pid ;
        String ssr = SystemUtil.execShellCmd("su root ps -A | grep "+appName).trim();
        Log.d("liujunjie",""+ssr);
        String ssr1 = ssr.substring(ssr.indexOf(" "),ssr.length()).trim();
        ssr = ssr1.substring(0,ssr1.indexOf(" ")).trim();
        pid = Integer.parseInt(ssr);

        return pid;
    }
    public List<String[]> getStartAndEnd(int pid ){
        //这里实现过滤了二层，过滤了写的操作

        String ff  = SystemUtil.execShellCmd("su root grep -v deleted -rsn proc/"+pid+"/maps | grep rw-p");
        // Log.d("liujunjie",ff);
        List<String[]> list = new ArrayList<>();
        //数据处理
        String[] maps = ff.split("\n");
        for (int i = 0; i < maps.length; i++) {

            String tt = maps[i];
            String starTt = tt.substring(tt.indexOf(":")+1,tt.indexOf("-"));
            starTt = starTt.substring(starTt.indexOf(":")+1,starTt.length());
            //long statr = Long.parseLong(starTt,16);
            String endStr  = tt.substring(tt.indexOf("-")+1,tt.indexOf(" "));
           // long end = Long.parseLong(endStr,16);
            String[]  mapsLong = new String[]{starTt,endStr};
            list.add(mapsLong);

        }


        return list;
    }

}