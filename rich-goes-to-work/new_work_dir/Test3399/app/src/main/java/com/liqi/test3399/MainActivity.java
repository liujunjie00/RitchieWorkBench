package com.liqi.test3399;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.liqi.test3399.fragment.FragmentBluetooth;
import com.liqi.test3399.fragment.FragmentBrightness;
import com.liqi.test3399.fragment.FragmentCamera;
import com.liqi.test3399.fragment.FragmentDisk;
import com.liqi.test3399.fragment.FragmentGravity;
import com.liqi.test3399.fragment.FragmentHardwareInformation;
import com.liqi.test3399.fragment.FragmentLCD;
import com.liqi.test3399.fragment.FragmentNetworkPort;
import com.liqi.test3399.fragment.FragmentRecord;
import com.liqi.test3399.fragment.FragmentSpeark;
import com.liqi.test3399.fragment.FragmentTouch;
import com.liqi.test3399.fragment.FragmentVersionView;
import com.liqi.test3399.fragment.FragmentVideo;
import com.liqi.test3399.fragment.FragmentWifi;
import com.liqi.test3399.ui.UIYtils;
import com.liqi.test3399.view.PointerLocationView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private Fragment fragmentVersion,fragmentHardware_information,
    fragmentLcd,fragmentTouch,fragmentTrumpet,fragmentBluetooth,
    fragmentDisk,fragmentGravity,fragmentBrightness,fragmentVideo;
    //fragmentCamera fragmentRecording, fragmentWifi
    private FragmentCamera fragmentCamera1;
    private FragmentRecord fragmentRecord;
    private FragmentWifi fragmentWifi;
    private PointerLocationView mPointerView;
    private FragmentNetworkPort networkPort;
    private int exit = 0;
    // 设置权限
    private static final int GETPERMS = 100;
    private String[] perms;
    private Handler permissionsHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置屏幕尺寸适配
        UIYtils.setDensity(getApplication(),this);



        //设置权限
        perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.BLUETOOTH_SCAN
                };

        checkPermission();
        //设置标题
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置没有标题
        setContentView(R.layout.activity_main);
        Fragment[] fragments = {fragmentVersion,fragmentHardware_information,
                fragmentLcd,fragmentTouch,fragmentTrumpet,fragmentBluetooth,
                fragmentDisk,fragmentGravity,fragmentBrightness,fragmentVideo};
        //fragmentCamera fragmentRecording,fragmentWifi
        List fragmentView = new ArrayList<Fragment>(13);
        //main窗口的声明
        //Fragment fragmentWif = new FragmentWifi(R.layout.wifi_fragment);
        Fragment fragmentVersion = new FragmentVersionView(R.layout.version_fragment);
        Fragment fragmentHardwareInformation = new FragmentHardwareInformation(R.layout.hardware_information_fragment);
        Fragment fragmentLCD = new FragmentLCD(R.layout.lcd_fragment);
        //fragmentCamera1 = new FragmentCamera(R.layout.camera_fragment);

        Fragment fragmentTouch = new FragmentTouch(R.layout.touch_fragment);
        Fragment fragmentSpeark = new FragmentSpeark(R.layout.speark_fragment);
        Fragment fragmentBluetooth = new FragmentBluetooth(R.layout.bluetooth_fragment);
        Fragment fragmentDisk = new FragmentDisk(R.layout.disk_fragment);
        Fragment fragmentGravity = new FragmentGravity(R.layout.gravity_fragment);
        // Fragment fragmentRecord = new FragmentRecord(R.layout.recording_fragment);
        Fragment fragmentBrightness = new FragmentBrightness(R.layout.brightness_fragment);
        Fragment fragmentVideo = new FragmentVideo(R.layout.video_fragment);
        //fragmentView.add(fragmentWif);
        fragmentView.add(fragmentVersion);
        fragmentView.add(fragmentHardwareInformation);
        fragmentView.add(fragmentLCD);
        // fragmentView.add(fragmentCamera1);
        fragmentView.add(fragmentTouch);
        fragmentView.add(fragmentSpeark);
        fragmentView.add(fragmentBluetooth);
        fragmentView.add(fragmentDisk);
        fragmentView.add(fragmentGravity);
        //fragmentView.add(fragmentRecord);
        fragmentView.add(fragmentBrightness);
        fragmentView.add(fragmentVideo);

        // 窗口支架类
        int[] layoutID = {R.id.version,R.id.hardware_information,R.id.lcd,R.id.touch,
                R.id.trumpet,R.id.Bluetooth,R.id.disk,R.id.gravity,
                R.id.brightness,R.id.video
        };
        //R.id.camera, R.id.recording,
        //设置窗口强制
        getWindow().addFlags(FLAG_FULLSCREEN );
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //设置全屏 打开划线的功能
        mPointerView = (PointerLocationView) findViewById(R.id.pointerview);
        mPointerView.setBackgroundColor(Color.TRANSPARENT);// transparent透明
        mPointerView
                .setOnPointCountChangeListener(new PointerLocationView.OnPointCountChangeListener() {

                    public void onPointCountChange(int newPointCount) {
                        if (newPointCount >= 20) {
                        }
                    }
                });
        //main窗口所有fragment元素的id

        for (int i = 0; i < fragments.length; i++) {
            replaceView(fragments[i],layoutID[i],(Fragment)fragmentView.get(i));
        }
        //后加的网口窗口
        loadNetworkPortView();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GETPERMS) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //replaceView(null,R.id.camera,);

                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                    //System.exit(0);
                }
            }
            loadCameraView();
            loadRecordView();
            loadWifiView();
        }

    }

    //推出程序
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "再次返回退出程序", Toast.LENGTH_SHORT).show();
        exit++;
        if (exit==2){
            System.exit(0);
        }
    }
    //判断是否有相关权限，并申请权限
    public void checkPermission() {

        if (!EasyPermissions.hasPermissions(this, perms)) {
           /* permissionsHandler.post(new Runnable() {
                @Override
                public void run() {
                    fragmentCamera1.reviewCamera();
                }
            });*/
            ActivityCompat.requestPermissions(MainActivity.this, perms, GETPERMS);
        }else {
            loadCameraView();
            loadRecordView();
            loadWifiView();
        }
    }
    // 加载相机视图界面
    public void loadCameraView(){
        fragmentCamera1 = new FragmentCamera(R.layout.camera_fragment);
        replaceView(fragmentCamera1,R.id.camera,fragmentCamera1);
    }
    public void loadRecordView(){
        fragmentRecord = new FragmentRecord(R.layout.recording_fragment);
        replaceView(fragmentRecord,R.id.recording,fragmentRecord);
    }
    public void loadWifiView(){
        fragmentWifi = new FragmentWifi(R.layout.wifi_fragment);
        replaceView(fragmentWifi,R.id.wifi,fragmentWifi);
    }
    public void loadNetworkPortView(){
        networkPort = new FragmentNetworkPort(R.layout.network_port_fragment);
        replaceView(networkPort,R.id.network_port,networkPort);
    }

    public void replaceView(Fragment fragmentmain,int fragmentViewid,Fragment Fragmentclass){
        if (fragmentmain == null){
            fragmentmain =  Fragmentclass;
        }
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(false)
                //这里的参数需要使用一个Fragment,我们需要再前面去实例化
                .replace(fragmentViewid,fragmentmain)
                .addToBackStack("sss"+fragmentViewid)
                .commitAllowingStateLoss();
    }
}