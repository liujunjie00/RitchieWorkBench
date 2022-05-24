package com.liqi.test3399.fragment;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;

public class FragmentBrightness extends Fragment {
    private Context context;
    int windowBrightness;
    private Activity activity;
    int min_rightness_level_count=10;
    int num = 0;
    private ProgressBar progressBar;
    private TextView textView1;
    Handler handler2 = new Handler(Looper.myLooper());


    public FragmentBrightness(int brightness_fragment) {
        super(brightness_fragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public FragmentBrightness() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = this.getActivity();
        windowBrightness = getWindowBrightness();
        Button button1 = view.findViewById(R.id.brightness_btn1);
        Button button2 = view.findViewById(R.id.brightness_btn2);
        Button button3 = view.findViewById(R.id.brightness_btn3);
        textView1 = view.findViewById(R.id.brightness_wink);
         progressBar = view.findViewById(R.id.ProgressBar);
        handler2.postDelayed(runnable1,70);
    }

    Runnable runnable1  = new Runnable() {
        @Override
        public void run() {

             if (min_rightness_level_count == 10){
                num = 5;
            }
            if (min_rightness_level_count == 250){
                num = -5;
            }
            min_rightness_level_count = min_rightness_level_count+num;
            if (num>0){
                textView1.setText("屏幕亮度增加中");
            }else {
                textView1.setText("屏幕亮度减小中");
            }
            setBrightness(min_rightness_level_count);
            progressBar.setProgress(min_rightness_level_count);
            handler2.postDelayed(runnable1,70);
        }
    };
    private boolean isAutoBrightness(Context context) {
        ContentResolver resolver = context.getContentResolver();
        boolean automicBrightness = false;

        try {
            automicBrightness = Settings.System.getInt(resolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }
    private int getWindowBrightness(){
        int nowBrightnessValue = 0;
        ContentResolver resolver = context.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }
    private void setBrightness(int brightness) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }
}
