package com.liqi.test3399.fragment;

import static android.content.Context.BLUETOOTH_SERVICE;

import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;


public class FragmentBluetooth extends Fragment {
    private View bluetooth_layout;
    private Context context;
    String deviceName;
    TextView textView,bt_name;
    StringBuilder bt_names = new StringBuilder();
    private Handler handler = new Handler(Looper.myLooper());


    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter  bluetoothAdapter;



    public FragmentBluetooth(int bluetooth_fragment) {
        super(bluetooth_fragment);
    }

    public FragmentBluetooth() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.bluetooth_textView);
        bt_name = view.findViewById(R.id.bt_name);
        bluetooth_layout = view.findViewById(R.id.bluetooth_layout);
        handler.postDelayed(runnable,3*1000);

    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                if (deviceName != null && !bt_names.toString().contains(deviceName)){
                    bt_names = bt_names.append("蓝牙名称："+deviceName+"\n");
                    bt_name.setText(bt_names.toString());
                }

            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mBluetoothManager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
            bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter == null){
                textView.setText("该设备不支持蓝牙");
                bluetooth_layout.setBackgroundColor(Color.RED);
                return;
            }
            if (!bluetoothAdapter.isEnabled()){ //检查是否开启蓝牙功能
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent); //跳转到系统Activity，让用户选择开启蓝牙功能
                // bluetoothAdapter.enable();
                return;
            }else {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                context.registerReceiver(receiver, filter);
                bluetoothAdapter.startDiscovery();
            }
            //handler.postDelayed(runnable,15*1000);
        }

    };

    }




