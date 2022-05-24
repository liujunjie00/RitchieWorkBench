package com.liqi.test3399.btuntil;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liqi.test3399.R;

import java.util.List;

public class BtAdapter extends BaseAdapter {

    private List<BluetoothDevice> mDeviceList;
    private Context mContext;
    private LayoutInflater mInflater;

    public BtAdapter(List<BluetoothDevice> deviceList,Context context){
        this.mDeviceList=deviceList;
        this.mContext=context;
        this.mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mDeviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDeviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler viewHandler;
        if(convertView==null){
            viewHandler=new ViewHandler();
            convertView=mInflater.inflate(R.layout.bt_item,null);
            viewHandler.textView=convertView.findViewById(R.id.ble_device);
            convertView.setTag(viewHandler);
        }else{
            viewHandler=(ViewHandler)convertView.getTag();
        }

        viewHandler.textView.setText(mDeviceList.get(position).getName());

        return convertView;
    }

    private class ViewHandler{
        TextView textView;
    }
}

