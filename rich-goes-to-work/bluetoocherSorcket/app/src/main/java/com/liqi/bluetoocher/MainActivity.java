package com.liqi.bluetoocher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button off_bluetooth_button,show_bluetooth_name1_button,
            show_bluetooth_name2_button,show_bluetooth_name3_button,
            msg_request;
    private EditText data1;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private String[] perms;
    private int requestCode;
    BluetoothGatt bluetoothGatt;
    private List serviceslist;
    //上下文对象
    private BluetoothGattService bluetoothGattServices;
    //服务下的特征（characteristic）
    private BluetoothGattCharacteristic characteristic_zd, characteristic_jb;
    private List<BluetoothDevice> deviceList = new ArrayList<>();
    private List<BluetoothGattService> services ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //手动获取权限
        perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH
        };
        checkPermission();
        //获取权限
        off_bluetooth_button = findViewById(R.id.off_bluetooth_button);
        show_bluetooth_name1_button = findViewById(R.id.show_bluetooth_name1_button);
        show_bluetooth_name2_button = findViewById(R.id.show_bluetooth_name2_button);
        show_bluetooth_name3_button = findViewById(R.id.show_bluetooth_name3_button);
        msg_request = findViewById(R.id.msg_request);
        data1 = findViewById(R.id.data1);
        //设置打开蓝牙
        off_bluetooth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开蓝牙，扫描设备

                /*mBluetoothManager = (BluetoothManager) getApplicationContext().getSystemService(BLUETOOTH_SERVICE);
                bluetoothAdapter = mBluetoothManager.getAdapter();

                if (bluetoothAdapter == null) {
                    show_bluetooth_name1_button.setText("该设备不支持蓝牙");
                    return;
                }
                if (!bluetoothAdapter.isEnabled()) { //检查是否开启蓝牙功能
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, requestCode);
                    return;
                } else {
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    //getApplicationContext().registerReceiver(receiver, filter);
                    bluetoothAdapter.startLeScan(callback);
                }
                Log.d("liujunjie","被点击了");*/

                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });


        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.requestCode =requestCode;
    }

   /* private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();//设备名称
                String deviceHardwareAddress = device.getAddress(); // MAC address
                //if (deviceName.contains())
                if (deviceName == null) return;
                if (deviceName.contains("101")){
                    bluetoothGatt = device.connectGatt(getApplicationContext(), true, new BluetoothGattCallback() {
                        @Override
                        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                        }

                        @Override //改变状态时的回调方法
                        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                            super.onConnectionStateChange(gatt, status, newState);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String status;
                                    //根据返回的状态更新设置
                                    switch (newState) {
                                        case BluetoothGatt.STATE_CONNECTED:
                                           Log.d("liujunjie","已经连接");
                                            //因为已经连接所以停止调用适配器的LeScan方法

                                            //获取连接设备的服务
                                            bluetoothGatt.discoverServices();
                                            break;
                                        case BluetoothGatt.STATE_CONNECTING:
                                            Log.d("liujunjie","正在连接");
                                            break;
                                        case BluetoothGatt.STATE_DISCONNECTED:
                                            Log.d("liujunjie","已经断开");
                                            break;
                                    }
                                }
                            });
                        }



                        @Override
                        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                            super.onCharacteristicRead(gatt, characteristic, status);
                        }

                        @Override
                        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                            super.onCharacteristicWrite(gatt, characteristic, status);
                        }
                    });
                }



            }
        }
    };*/

    public void checkPermission() {
        for (int i = 0; i < perms.length; i++) {
            int status = ContextCompat.checkSelfPermission(getApplicationContext(),perms[i]);
            if (status != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,perms,requestCode);
                return;
            }

        }

    }
    public BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
          /*  //使用contains方法查看当前扫描到的设备是否已经在列表中,如果不在就添加
            if (!deviceList.contains(bluetoothDevice)) {
                //将设备加入列表中,通过适配器显示
                deviceList.add(bluetoothDevice);
                //list.setAdapter(new BsAdapter(MainActivity.this, deviceList));
                Handler handler = new Handler(Looper.myLooper());
                handler.post(runnable2);
            }*/
            if (bluetoothDevice.getName() == null) return;
            String devicename = bluetoothDevice.getName();
            if (devicename.contains("101")){
                deviceList.add(bluetoothDevice);
                /*Handler handler = new Handler(Looper.myLooper());
                handler.post(runnable2);*/
               // bluetoothAdapter.stopLeScan(this);
                bluetoothGatt = bluetoothDevice.connectGatt(getApplicationContext(),true,bluetoothGattCallback);
                Log.d("liujunjie",bluetoothGatt.toString()+"bluetoothDevice服务");

            }
        }
    };
    public BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
            Log.d("liujunjie","onPhyUpdate");
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
            Log.d("liujunjie","onPhyRead");
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d("liujunjie","onConnectionStateChange");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d("liujunjie", "连接成功");
                services = gatt.getServices();
               /* Handler handler2 = new Handler(Looper.myLooper());
                handler2.post(runnable1);*/


            }
        }
        @Override//扫描到蓝牙设备时的结果
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d("liujunjie","onServicesDiscovered");
            /*if (status == BluetoothGatt.GATT_SUCCESS){
                Log.d("liujunjie","连接成功");
                 services = bluetoothGatt.getServices();
                Handler handler2 = new Handler(Looper.myLooper());
                handler2.post(runnable1);


            }*/
            for (final BluetoothGattService bluetoothGattService : services) {
                bluetoothGattServices = bluetoothGattService;
                Log.i("liujunjie", "onServicesDiscovered: " + bluetoothGattService.getUuid());//输出服务对应的uuid
                //将当前遍历的服务的characteristic放入list中
                List<BluetoothGattCharacteristic> charc = bluetoothGattService.getCharacteristics();
                //遍历当前服务下的所有characteristic
                for (BluetoothGattCharacteristic charac : charc) {
                    Log.i("liujunjie", "run: " + charac.getUuid());//输出当前遍历到的characteristic的特征值
                    if (charac.getUuid().toString().equals("对应的UUID"))//放入对应的特征的uuid
                    {
                        //此处为确定某个特征之后对应的操作,下面同理
                        bluetoothGatt.readCharacteristic(charac);//这里代表对目标特征进行读操作,当与连接设备进行读操作的时候回调方法
                    }
                    else if (charac.getUuid().toString().equals("对应的UUID"))
                    {
                    }
                    else if (charac.getUuid().toString().equals("对应的UUID"))
                    {
                    }
                }
                serviceslist.add(bluetoothGattService.getUuid().toString());
            }

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d("liujunjie","onCharacteristicRead");
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d("liujunjie","onCharacteristicWrite");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.d("liujunjie","onCharacteristicChanged");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.d("liujunjie","onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d("liujunjie","onDescriptorWrite");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.d("liujunjie","onReliableWriteCompleted");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.d("liujunjie","onReadRemoteRssi");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.d("liujunjie","onMtuChanged");
        }
    };
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            for (final BluetoothGattService bluetoothGattService : services) {
                bluetoothGattServices = bluetoothGattService;
                Log.i("liujunjie", "onServicesDiscovered: " + bluetoothGattService.getUuid());//输出服务对应的uuid
                //将当前遍历的服务的characteristic放入list中
                List<BluetoothGattCharacteristic> charc = bluetoothGattService.getCharacteristics();
                //遍历当前服务下的所有characteristic
                for (BluetoothGattCharacteristic charac : charc) {
                    Log.i("liujunjie", "run: " + charac.getUuid());//输出当前遍历到的characteristic的特征值
                    if (charac.getUuid().toString().equals("对应的UUID"))//放入对应的特征的uuid
                    {
                        //此处为确定某个特征之后对应的操作,下面同理
                        bluetoothGatt.readCharacteristic(charac);//这里代表对目标特征进行读操作,当与连接设备进行读操作的时候回调方法
                    }
                    else if (charac.getUuid().toString().equals("对应的UUID"))
                    {
                    }
                    else if (charac.getUuid().toString().equals("对应的UUID"))
                    {
                    }
                }
                serviceslist.add(bluetoothGattService.getUuid().toString());
            }
        }
    };
    //连接gatt服务器
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {


        }
    };


}



