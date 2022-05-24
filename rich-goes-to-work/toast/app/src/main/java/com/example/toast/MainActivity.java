package com.example.toast;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1,button2,button3;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.ssr);
        button2 = findViewById(R.id.ssr2);
        button3 = findViewById(R.id.ssr3);
        seekBar = findViewById(R.id.sss);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ssr:
                    Toast toast = Toast.makeText(this,"安装成功!",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,400);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.toast_staly);
                    TextView toast1 = (TextView) toastView.findViewById(android.R.id.message);
                    toast1.setTextColor(Color.WHITE);
                    toast1.setTextSize(15);

                    toast.show();
                    break;
            case R.id.ssr2:
                //全屏设置
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
                if (getWindow() == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    getWindow().setAttributes(lp);
                    final View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                //全屏设置
                    View reboot = LayoutInflater.from(MainActivity.this).inflate(R.layout.reboot,null);
                    Dialog rebootBuilder = new Dialog(MainActivity.this);
                    String language1 = Locale.getDefault().getLanguage();
                    String defaultMessageOff = "OFF";
                    String defaultMessageRestar = "RESTART";
                    String defaultMessageBack = "BACK";
                    //en ja zh
                    if ("ja".contains(language1)){
                        defaultMessageOff = "電源オフ";
                        defaultMessageRestar= "再起動";
                        defaultMessageBack = "戻る";
                    }
                    if ("zh".contains(language1)){
                        defaultMessageOff = "关机";
                        defaultMessageRestar = "重启";
                        defaultMessageBack = "返回";
                    }
                    Button button1 = reboot.findViewById(R.id.button1);
                    Button button2 = reboot.findViewById(R.id.button2);
                    Button button3 = reboot.findViewById(R.id.button3);

                    button1.setText(defaultMessageOff);
                    button2.setText(defaultMessageRestar);
                    button3.setText(defaultMessageBack);
                    rebootBuilder.setContentView(reboot);
                    rebootBuilder.create();
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           /* Intent ssintrnt = new Intent(Intent.ACTION_SHUTDOWN);
                            ssintrnt.putExtra("EXTRA_KEY_CONFIRM", true);//其中false换成true,会弹出是否关机的确认窗口
                            ssintrnt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sendBroadcast(ssintrnt);*/
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent ssrintrnt = new Intent();
                            ssrintrnt.setAction(Intent.ACTION_REBOOT);
                            ssrintrnt.putExtra("nowait",1);
                            ssrintrnt.putExtra("interval",1);
                            ssrintrnt.putExtra("startTime",1);
                            ssrintrnt.putExtra("window",0);
                            getApplicationContext().sendBroadcast(ssrintrnt);*/

                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rebootBuilder.cancel();

                        }
                    });
                    View.OnTouchListener touchListener = new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Button button = (Button)v;
                            //Log.d("liujunjie","数值"+event);
                            int eventssr = event.getAction();
                            Log.d("liujunjie","数值"+eventssr);
                            /*if (eventssr == MotionEvent.ACTION_DOWN){
                                button.setTextColor(Color.WHITE);
                            }else if (eventssr == MotionEvent.ACTION_MOVE){
                                button.setTextColor(Color.WHITE);

                            }else {
                                button.setTextColor(Color.BLACK);
                            }*/
                            if (eventssr == MotionEvent.ACTION_BUTTON_PRESS){
                                button.setTextColor(Color.WHITE);

                            }else {
                                button.setTextColor(Color.BLACK);
                            }




                            return false;
                        }
                    };

                    button1.setOnTouchListener(touchListener);
                    button2.setOnTouchListener(touchListener);
                    button3.setOnTouchListener(touchListener);
                    final Window window = rebootBuilder.getWindow();
                    //window.setBackgroundDrawable(new ColorDrawable(0));
                    rebootBuilder.show();
                    rebootBuilder.getWindow().setGravity(Gravity.TOP);
                    break;



                case R.id.ssr3:
                    View home = LayoutInflater.from(MainActivity.this).inflate(R.layout.home,null);
                    Dialog homeBuilder = new Dialog(MainActivity.this);
                    Button button = home.findViewById(R.id.home_btn);
                    TextView textView = home.findViewById(R.id.home_hint);
                    int uiOptionss = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
                    this.getWindow().getDecorView().setSystemUiVisibility(uiOptionss);
                    if (getWindow() == null) {
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                        getWindow().setAttributes(lp);
                        final View decorView = getWindow().getDecorView();
                        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                    homeBuilder.setContentView(home);
                    homeBuilder.create();
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //杀死其他进程回到主页
                        }
                    });
                    String language = Locale.getDefault().getLanguage();
                    String defaultMessage = "RETURN TO THE HOME SCREEN \n" +
                            "AND EXIT THE APP";
                    //en ja zh
                    if ("ja".contains(language)){
                        defaultMessage = "ホーム画面に戻り、アプリが終了します";
                    }
                    if ("zh".contains(language)){
                        defaultMessage = "回首页 或 关机其他应用";
                    }
                    textView.setText(defaultMessage);

                    homeBuilder.show();
                    WindowManager.LayoutParams params = homeBuilder.getWindow().getAttributes();
                    homeBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    params.width = 1080;
                   // params.height = 800 ;

                    homeBuilder.getWindow().setAttributes(params);
                    homeBuilder.getWindow().setGravity(48);





        }

    }
}