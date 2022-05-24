package com.liqi.gamejniread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.gamejniread.until.SystemUtil;
import com.liqi.nativelib.NativeLib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class MainActivity extends AppCompatActivity {
private Button button1,button2;
public int data = 5635;
private Handler handler;
private List<long[]> sst;
private List<long[]> ttu;
private NativeLib nativeLib;

private int pidJava;
private TextView textView;
public String str= "13";
public int[] rr = new int[]{1,2,3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        pidJava  = getPidKK("com.liqi.gamejniread");
        sst  = getStartAndEnd(pidJava);
        handler = new Handler(Looper.myLooper());
        nativeLib = new NativeLib();
        textView = findViewById(R.id.textview1);
        nativeLib.testIntAddr(data);
        handler.post(runnable2);
        nativeLib.test1arry(rr);
        Log.d("liujunjie",rr[0]+"数组的地址");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pid = getPidKK("com.liqi.gamejniread");
                ///int sssr = nativeLib.ModifyMemory();
                //int ssr2 = nativeLib.ModifyMemory2(pid);
                button1.setText("  "+data+"\n"+"程序的pid是"+pid);
                handler.post(runnable);
                handler.post(runnable2);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttu = getStartAndEnd( 8729);
                Handler handler2 = new Handler(Looper.myLooper());
                handler2.post(runnable3);

            }
        });
    }
    // 这个appName 表示的是app包名 例子 com.liqi.gamejniread
    public int getPidKK(String appName){
        int pid ;
        String ssr = SystemUtil.execShellCmd("su root ps -A | grep "+appName).trim();
        Log.d("liujunjie",""+ssr);
        String ssr1 = ssr.substring(ssr.indexOf(" "),ssr.length()).trim();
        ssr = ssr1.substring(0,ssr1.indexOf(" ")).trim();
        pid = Integer.parseInt(ssr);

        return pid;
    }

    public List<long[]> getStartAndEnd(int pid ){
        //这里实现过滤了二层，过滤了写的操作

        SystemUtil.execShellCmd("touch see.txt ");
        String ff  = SystemUtil.execShellCmd("su root grep -v deleted -rsn proc/"+pid+"/maps | grep rw-p");
       // Log.d("liujunjie",ff);
        List<long[]> list = new ArrayList<>();
        //数据处理
        String[] maps = ff.split("\n");
        for (int i = 0; i < maps.length; i++) {

            String tt = maps[i];
            String starTt = tt.substring(tt.indexOf(":")+1,tt.indexOf("-"));
                starTt = starTt.substring(starTt.indexOf(":")+1,starTt.length());
            long statr = Long.parseLong(starTt,16);
            String endStr  = tt.substring(tt.indexOf("-")+1,tt.indexOf(" "));
            long end = Long.parseLong(endStr,16);
            long[]  mapsLong = new long[]{statr,end};
            list.add(mapsLong);

        }

        return list;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //String ssr = SystemUtil.execShellCmd("./system/bin/a.out "+pidJava+" "+sst.get(0)[0]+" "+sst.get(0)[1]+" "+8652);
            //nativeLib.ModifyMemory3(pidJava,sst.get(0)[0],sst.get(0)[1]);
            for (int i = 0; i < sst.size()-1; i++) {
                //count =nativeLib.ModifyMemory3(pidJava,sst.get(i)[0],sst.get(i)[1]);
                String ssr = SystemUtil.execShellCmd("./system/bin/a.out "+pidJava+" "+sst.get(i)[0]+" "+sst.get(i)[1]+" "+5635);
                ssr = ssr.substring(ssr.indexOf(".")+4,ssr.length());
                Log.d("liujunjie","内存"+sst.get(i)[0]+":"+sst.get(i)[1]+"返回值是"+ssr);
            }
        }
    };

    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {

            //String ssr = SystemUtil.execShellCmd("./system/bin/a.out "+pidJava+" "+sst.get(0)[0]+" "+sst.get(0)[1]+" "+8652);
            //nativeLib.ModifyMemory3(pidJava,sst.get(0)[0],sst.get(0)[1]);
            for (int i = 0; i < sst.size()-1; i++) {
                //count =nativeLib.ModifyMemory3(pidJava,sst.get(i)[0],sst.get(i)[1]);
                String ssr = SystemUtil.execShellCmd("su root ./system/bin/a.out "+10124+" "+ttu.get(i)[0]+" "+ttu.get(i)[1]+" "+14);
                ssr = ssr.substring(ssr.indexOf(".")+4,ssr.length());
                Log.d("liujunjie","内存"+ttu.get(i)[0]+":"+ttu.get(i)[1]+"返回值是"+ssr);
            }
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            Date sysdate = new Date();
            textView.setText( "int data = "+data+"\n"+String.format("%tF %tT", sysdate, sysdate)+"\n hashcode:"+System.identityHashCode(str));
            Log.d("liujunjie", "run:  "+data);

            handler.postDelayed(this::run,1*1000);

        }
    };
    Runnable printDataForTextView = new Runnable() {
        @Override
        public void run() {
            //nativeLib.printfI();
        }
    };



}