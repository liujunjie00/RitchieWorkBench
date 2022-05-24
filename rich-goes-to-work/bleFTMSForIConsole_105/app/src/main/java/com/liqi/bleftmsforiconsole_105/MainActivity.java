package com.liqi.bleftmsforiconsole_105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.NativeActivity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.ListScanCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.liqi.nativelib.NativeLib;



public class MainActivity extends AppCompatActivity{
    private String[] perms;
    private int requestCode;
    private List<Map<String, Object>> deviceShow;
    private BleManager bleManager;
    private BluetoothDevice[] devicesName;
    private String onclickBleName;

    private Button button;
    private ListView listView;
    private ShowFragement fragment;
    private boolean scanStatus = false; //表示扫描的状态
    private SimpleAdapter simpleAdapter;
    private BluetoothGatt bluetoothGatt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.scan);
        listView = findViewById(R.id.device_list);

        NativeLib nn = new NativeLib();

        String bb = nn.stringFromJNI();
        //cpuFromJNI("192.168.0.163");

        // 获取权限
        checkPermission();
        //扫描蓝牙设备展示list
        deviceShow = new ArrayList<>();
        bleManager = new BleManager(this);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!scanStatus) {
                    deviceShow.clear();
                    if (simpleAdapter != null){
                        simpleAdapter = null;
                    }
                    listView.setVisibility(View.GONE);
                    if (bluetoothGatt != null){
                        bluetoothGatt.close();
                        bluetoothGatt =null;
                    }
                    scanStatus = true;
                    Toast.makeText(getApplicationContext(), "开始扫描", Toast.LENGTH_SHORT).show();
                    bleManager.scanDevice(new ListScanCallback(5 * 1000) {
                        @Override
                        public void onScanTimeout() {
                            super.onScanTimeout();
                            scanStatus = false;
                        }

                        @Override
                        public void onDeviceFound(BluetoothDevice[] devices) {
                            scanStatus = false;
                            devicesName = devices;
                            for (int i = 0; i < devicesName.length; i++) {
                                HashMap<String, Object> deviceItemMap = new HashMap<>();
                                if (devicesName[i].getName() != null) {
                                    String name = devicesName[i].getName();
                                    deviceItemMap.put("name", name);
                                    deviceShow.add(deviceItemMap);
                                }
                            }
                            simpleAdapter = new SimpleAdapter(getApplicationContext(), deviceShow, R.layout.device_item, new String[]{"name"}, new int[]{R.id.device_name});
                            listView.setAdapter(simpleAdapter);
                            listView.setOnItemClickListener(new OnItemclick());
                            listView.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "扫描结束", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "正在扫描中，不用重复点击", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //监听list元素绑定事件

        //点击item建立连接

        //接受蓝牙发送的数据处理


        //将数据加载到show


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.requestCode = requestCode;
    }

    public void checkPermission() {
        perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
        };
        for (int i = 0; i < perms.length; i++) {
            int status = ContextCompat.checkSelfPermission(getApplicationContext(), perms[i]);
            if (status != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, perms, requestCode);
                return;
            }

        }

    }
    class OnItemclick implements AdapterView.OnItemClickListener {

        public OnItemclick() {
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (bluetoothGatt != null){
                bluetoothGatt.close();
                bluetoothGatt =null;
            }
            TextView textView = view.findViewById(R.id.device_name);
            onclickBleName= (String) textView.getText();
            for (int j = 0; j < devicesName.length; j++) {
                if (onclickBleName.equals(devicesName[j].getName())){
                    bleManager.connectDevice(devicesName[j], false, new BleGattCallback() {
                        @Override
                        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                            super.onServicesDiscovered(gatt, status);
                            bluetoothGatt = gatt;
                            gatt.getServices().size();
                            Log.d("liujunjie","连接成功"+gatt.getServices().size());
                            fragment = new ShowFragement(R.layout.show_data_fragrament,getApplicationContext(), gatt,bleManager);
                           getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(false)
                                   .replace(R.id.msg,fragment)
                                   .commitAllowingStateLoss();

                        }

                        @Override
                        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                            Log.d("liujunjie","数据发生改变");
                            super.onCharacteristicChanged(gatt, characteristic);

                        }

                        @Override
                        public void onNotFoundDevice() {

                        }

                        @Override
                        public void onFoundDevice(BluetoothDevice device) {

                        }

                        @Override
                        public void onConnectSuccess(BluetoothGatt gatt, int status) {
                            //连接成功 初始化动作
                            gatt.discoverServices();

                        }

                        @Override
                        public void onConnectFailure(BleException exception) {

                        }
                    });

                }
            }

        }
    }

}