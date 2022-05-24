package com.liqi.bleftmsforiconsole_105;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.conn.BleConnector;
import com.clj.fastble.exception.BleException;
import com.liqi.bleftmsforiconsole_105.proFile.BikeData;
import com.liqi.bleftmsforiconsole_105.proFile.BikeData1;
import com.liqi.bleftmsforiconsole_105.proFile.IconsoleProFile;
import com.liqi.bleftmsforiconsole_105.uuid.BleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowFragement extends Fragment {
    private Context context;
    private BluetoothGatt bluetoothGatt;
    private List<BluetoothGattService> bluetoothGattServiceList;
    private List<BleMap> uuids;
    private BleManager bleManager;
    private String deviceName;
    private TextView textView,textView1,textView2,textView3,textView4;
    private int countNotfy,notfyIndex = 0;
    private Button button1;
    private boolean isNotyIng = false;
    private List<Integer> notycount;
    private int jj = 0;
    private  IconsoleProFile iconsoleProFile;

    public ShowFragement() {
    }

    public ShowFragement(Context context, BluetoothGatt bluetoothGatt) {
        this.context = context;
        this.bluetoothGatt = bluetoothGatt;
    }

    public ShowFragement(int contentLayoutId, Context context, BluetoothGatt bluetoothGatt,BleManager bleManager) {
        super(contentLayoutId);
        this.context = context;
        this.bluetoothGatt = bluetoothGatt;
        this.bleManager = bleManager;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView =view.findViewById(R.id.device_name);
        textView1 = view.findViewById(R.id.data);
        textView2 = view.findViewById(R.id.data1);
        textView3 = view.findViewById(R.id.data2);
        button1 = view.findViewById(R.id.button_1);


        init();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void init(){
        if (uuids != null){
            uuids.clear();
        }
        uuids = new ArrayList<>();
        //拿到6个服务
        bluetoothGattServiceList = bluetoothGatt.getServices();
        deviceName = bluetoothGatt.getDevice().getName();
        textView.setText("设备名称是"+deviceName);
        //遍历每一个服务
        for (int i = 0; i < bluetoothGattServiceList.size(); i++) {
            //拿到每一个服务下面的特征集合
            List<BluetoothGattCharacteristic> sb= bluetoothGattServiceList.get(i).getCharacteristics();
            // 遍历特征集合
            for (int j = 0; j < sb.size(); j++) {
                //拿到每一个特征集合的uuid
                UUID tz = sb.get(j).getUuid();
                String charUUID = tz.toString();
                //拿到每一个服务的uuid
                UUID service = bluetoothGattServiceList.get(i).getUuid();
                String serviceUUID = service.toString();
                // 把名字写出来
                String name = "第 "+i+"个服务，"+"第"+j+"个特征";
                // 属性 未知
                String properties = BleMap.BUXIAODE;
                int pro = sb.get(j).getProperties();
                if (pro ==BluetoothGattCharacteristic.PROPERTY_NOTIFY ){
                    properties=BleMap.NOTIFY;
                    countNotfy++;
                }
                if (pro == BluetoothGattCharacteristic.PROPERTY_READ){
                    properties=BleMap.READ;

                }
                if (pro ==BluetoothGattCharacteristic.PROPERTY_WRITE){
                    properties=BleMap.WRITE;
                }
                uuids.add(new BleMap(name,serviceUUID,charUUID,properties));
            }

        }
        textView2.setText("该设备一共有"+(countNotfy)+"通知");
        Toast.makeText(context, "正在连接"+deviceName, Toast.LENGTH_SHORT).show();
        if (deviceName.equals("iConsole+105")){
            notifyOneBle(uuids,13);

        }
        if(deviceName.equals("Smart47466500")){
            notifyOneBle(uuids,5);
        }
        if (deviceName.equals("40510-101")){
            notifyAllBleMaps(uuids);
        }


    }
    public void notifyAllBleMaps(List<BleMap> bleMaps){
        isNotyIng = true;
        for (int i = 0; i < bleMaps.size(); i++) { //00001816-0000-1000-8000-00805f9b34fb
                BleMap bleMap = bleMaps.get(i);
            if (bleMap.getProperties() == BleMap.NOTIFY){
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bleManager.notify(bleMap.getServiceUUID(), bleMap.getCharUUID(), new BleCharacterCallback() {
                            @Override
                            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                                byte[] sso = characteristic.getValue();
                                textView1.setText(IconsoleProFile.byte2Hex1(sso));


                            }
                            @Override
                            public void onFailure(BleException exception) {
                            }
                        });
                    }
                });
            }
        }
    }
    public void notifyOneBle(List<BleMap> bleMaps,int i){
        if (i>bleMaps.size()){
            return;
        }
        isNotyIng = true;
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String service = bleMaps.get(i).getServiceUUID();
                String charUUID = bleMaps.get(i).getCharUUID();
           bleManager.notify(service, charUUID, new BleCharacterCallback() {
               @Override
               public void onSuccess(BluetoothGattCharacteristic characteristic) {
                  byte[] dd = characteristic.getValue();
                   //byte[] dd = new byte[]{-2, 31, 6, 20, 0, 0, 33, 2, 0, 0, 104, 4, 0, 1, 0, 13, 2, 0, 0, 18, 0, 0, 0, 0, 0, 0, -5, 0, 0, 0};
                  // setTextforTextview1(dd);
                   setTextforTextview1(dd);

               }

               @Override
               public void onFailure(BleException exception) {
                   Log.d("liujunjie","绑定失败");
               }
           });
            }
        });
    }
    public void stopnotify(List<BleMap> bleMaps,int i){
        if (i>bleMaps.size()){
            return;
        }
        isNotyIng = false;
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String service = bleMaps.get(i).getServiceUUID();
                String charUUID = bleMaps.get(i).getCharUUID();
                bleManager.stopNotify(service,charUUID);
            }
        });
    }
    public void setTextforTextview1(byte[] ss){
         BikeData1 bikeData = iconsoleProFile.getBikeData2(ss);
        textView1.setText(bikeData.toString());

    }

}
