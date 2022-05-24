package com.liqi.test3399.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;
import com.liqi.test3399.wifiutil.WifiUtil;
import com.liqi.test3399.wifiutil.wifiAdapter;


import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentWifi extends Fragment {
    private Context context;
    private ListView wifiView;
    private TextView wifiText;
    private List<ScanResult> list;
    private WifiUtil  wifiUtil;
    private Handler handler =new Handler(Looper.myLooper());
    private Handler handler1 =new Handler(Looper.myLooper());
    public FragmentWifi(int wifi_fragment) {
        super(wifi_fragment);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public FragmentWifi() {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         wifiView = view.findViewById(R.id.wifi_list);
         wifiText = view.findViewById(R.id.wifi_title);
         wifiUtil = new WifiUtil(context);
         handler.postDelayed(runnable1,1*1000);

         //handler.postDelayed(runnable,3*1000);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int ssr = 0;
            if (list.size()<1&& ssr<5){
                handler.postDelayed(runnable1,1*1000);
                ssr++;
                return;
            }

            list = wifiUtil.getAroundWifiDeviceInfo();
            if (list.size() <1){

                wifiText.setText("请检查 位置信息 是否打开");
                wifiText.setTextColor(Color.RED);
                return;
            }
            List<Map<String,Object>> form = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                HashMap hashMapWifi = new HashMap();
                if (list.get(i).SSID.trim().length()>0 ){
                    if (list.get(i).level>=-45){
                        hashMapWifi.put("image",R.drawable.wifi2);
                        hashMapWifi.put("name",list.get(i).SSID);
                    }
                    if (list.get(i).level<-45 && list.get(i).level>= -65 ){
                        hashMapWifi.put("image",R.drawable.wifi1);
                        hashMapWifi.put("name",list.get(i).SSID);
                    }
                    if (list.get(i).level<-65 ){
                        hashMapWifi.put("image",R.drawable.wifi0);
                        hashMapWifi.put("name",list.get(i).SSID);
                    }
                    form.add(hashMapWifi);
                }

            }
            //这个form参数必须是一个泛型是string object的list集合
            SimpleAdapter listWifiAdapter = new wifiAdapter(context,form, R.layout.wifi_item,new String[]{"image","name"},new int[]{
                    R.id.wifi_logo,R.id.wifi_name
            });
            wifiView.setAdapter(listWifiAdapter);

        }
    };
    Runnable runnable1 =new Runnable() {
        @Override
        public void run() {

                list = wifiUtil.getAroundWifiDeviceInfo();


            handler.postDelayed(runnable,2*1000);
        }
    };
}
