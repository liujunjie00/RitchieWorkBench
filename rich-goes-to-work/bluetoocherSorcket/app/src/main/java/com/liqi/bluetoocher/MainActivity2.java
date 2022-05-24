package com.liqi.bluetoocher;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothHealth;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.ListScanCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity2 extends AppCompatActivity {
    private BleManager bleManager;
    private List<BluetoothGattService> services = new ArrayList<>();
    private List<BluetoothGattCharacteristic> bluetoothGattCharacteristics = new ArrayList<>();
    private List<BluetoothGattDescriptor> bluetootptors = new ArrayList<>();
    private String sportString;
    private String sportString2;
    private ArrayList<String> deviceList = new ArrayList();
    private BluetoothHealth bluetoothHealth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bleManager = new BleManager(this);
        Connect();


    }

    //扫描设备
    private ArrayList scan() {
        bleManager.scanDevice(new ListScanCallback(10 * 1000) {
            @Override
            public void onDeviceFound(BluetoothDevice[] devices) {
                for (int i = 0; i < devices.length; i++) {
                    if (devices[i].getName() != null) {
                        deviceList.add(devices[i].getName());
                    }
                }
            }
        });

        return deviceList;
    }

    // 创建连接
    private void Connect() {

        //40510-101  iConsole+105  连接指定名称的蓝牙设备
        bleManager.scanNameAndConnect("40510-101", 5 * 1000, true, new BleGattCallback() {
            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
                services = gatt.getServices();
                UUID ss = services.get(5).getUuid();
                sportString = ss.toString();
                Log.d("liujunjie", "uuid" + gatt.toString());
                for (int i = 0; i < services.size(); i++) {
                    //获取相应的特征集合
                    bluetoothGattCharacteristics = services.get(5).getCharacteristics();
                    for (int j = 0; j < bluetoothGattCharacteristics.size(); j++) {
                    }
                }
                sportString2 = bluetoothGattCharacteristics.get(0).getUuid().toString();
                Handler handler = new Handler(getMainLooper());
                handler.post(runnable);

            }

            @Override
            public void onNotFoundDevice() {

            }

            @Override
            public void onFoundDevice(BluetoothDevice device) {

            }

            @Override
            public void onConnectSuccess(BluetoothGatt gatt, int status) {

                Boolean vvvv = gatt.discoverServices();
                Log.d("liujunjie", "连接成功" + vvvv);
                services = gatt.getServices();

            }

            @Override
            public void onConnectFailure(BleException exception) {

            }
        });
    }
    private int lastXSportData = 0;
    private int lastYSportData = 0;

    Runnable runnable = new Runnable() {


        @Override
        public void run() {

            bleManager.notify(sportString, sportString2, new BleCharacterCallback() {
                @Override
                public void onSuccess(BluetoothGattCharacteristic characteristic) {
                    byte[] sss = characteristic.getValue();
                    String uuu = byte2Hex(sss);
                    //01 29 010000cf7d
                    String sportCount = uuu.substring(2,4);
                    String xSport = uuu.substring(10,12);
                    String ySport = uuu.substring(12,14);
                    int sportIntCount = Integer.parseInt(sportCount,16);
                    int xSportInt = Integer.parseInt(xSport,16);
                    int ySportInt = Integer.parseInt(ySport,16);
                    //计算瞬间速度
                    if (lastXSportData == 0){
                        lastXSportData = xSportInt;
                    }
                    if (lastYSportData == 0){
                        lastYSportData = ySportInt;
                    }


                    //求绝对值
                    lastXSportData = (lastXSportData - xSportInt<0)?-(lastXSportData - xSportInt):lastXSportData - xSportInt;
                    lastYSportData = (lastYSportData - ySportInt<0)?-(lastYSportData - ySportInt):lastYSportData - ySportInt;


                    Log.d("liujunjie", uuu);
                    Log.d("liujunjie", "加速度"+lastXSportData*lastYSportData);

                    lastYSportData = ySportInt;
                    lastXSportData = xSportInt;
                    characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8,1);


                }

                @Override
                public void onFailure(BleException exception) {

                }
            });
        }
    };

    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}