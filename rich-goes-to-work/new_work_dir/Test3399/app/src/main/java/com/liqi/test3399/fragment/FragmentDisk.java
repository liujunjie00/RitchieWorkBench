package com.liqi.test3399.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.liqi.test3399.R;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class FragmentDisk extends Fragment {
    private Context context;
    private List<String> totalExtends;
    private Handler handler = new Handler(Looper.myLooper());
    private StringBuilder uBuilder;
    private StringBuilder SDBuilder;
    private TextView disk_textview;
    private TextView sdcard_textview;
    private View disk_layout;
    public FragmentDisk(int disk_fragment) {
        super(disk_fragment);
    }

    public FragmentDisk() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uBuilder = new StringBuilder();
        SDBuilder = new StringBuilder();
        disk_textview = view.findViewById(R.id.u_disk_textview);
        sdcard_textview = view.findViewById(R.id.sdcard_textview);
         disk_layout = view.findViewById(R.id.disk_layout);


        /*if (i==2){
            disk_layout.setBackgroundColor(Color.RED);
        }*/
        handler.postDelayed(runnable,3*1000);

    }

    public static String formatForROMAndRAM(long blockSizeLongXBlockCountLong) {
        int ssr1 = 0;
        int ssr = (int) (blockSizeLongXBlockCountLong / 1024 / 1024 / 1024);
        int[] format = {0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
        for (int i = 0; i < format.length; i++) {
            if (ssr >= format[i] && ssr < format[i + 1]) {
                ssr1 = format[i + 1];
            }
        }
        if (ssr1 == 0) {
            if (blockSizeLongXBlockCountLong > 500000000 && blockSizeLongXBlockCountLong < 900000000)
                return "512MB";
            if (blockSizeLongXBlockCountLong > 200000000 && blockSizeLongXBlockCountLong < 500000000)
                return "256MB";
            if (blockSizeLongXBlockCountLong > 900000000 && blockSizeLongXBlockCountLong < 1100000000)
                return "1GB";
        }
        return ssr1 + "GB";
    }

    private List getExtendedMemoryPath(Context mContext) {
        List<String> totalExtends = new ArrayList<>();
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<StorageVolume> storageVolumeClazz = null;
        try {
            storageVolumeClazz = (Class<StorageVolume>) Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Method toString = storageVolumeClazz.getMethod("toString");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String storageVolumeElemmentInfo = (String) toString.invoke(storageVolumeElement);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable) {
                    totalExtends.add(storageVolumeElemmentInfo);
                    totalExtends.add(path);
                }
            }
            return totalExtends;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            totalExtends = getExtendedMemoryPath(context);
            if (totalExtends == null) {
                disk_layout.setBackgroundColor(Color.RED);
            }
            for (int i = 0; i < totalExtends.size(); i++) {

                if (totalExtends.get(i).contains("SD")) {
                    String sdPath = totalExtends.get(i + 1);
                    StatFs statFs = new StatFs(sdPath);
                    long blockSizeLong = statFs.getBlockSizeLong();
                    long BlockCountLong = statFs.getBlockCountLong();
                    String sdGB = formatForROMAndRAM(blockSizeLong * BlockCountLong);
                    SDBuilder.append("SD卡设备：" + sdGB + "\n");
                }
                if (totalExtends.get(i).contains("USB")) {

                    String uPath = totalExtends.get(i + 1);
                    StatFs statFs = new StatFs(uPath);
                    long blockSizeLong = statFs.getBlockSizeLong();
                    long BlockCountLong = statFs.getBlockCountLong();
                    String uGB = formatForROMAndRAM(blockSizeLong * BlockCountLong);
                    uBuilder.append("U盘设备：" + uGB + "\n");
                }
            }
            int i = 0;
            if (uBuilder.length()<1){
                disk_textview.setBackgroundColor(Color.RED);
                i++;

            }else {
                String ustring = uBuilder.toString();
                ustring = ustring.substring(0,ustring.length()-1);
                disk_textview.setText(ustring);
            }
            if (SDBuilder.length()<1){
                sdcard_textview.setBackgroundColor(Color.RED);
                i++;
            }else {
                sdcard_textview.setText(SDBuilder);
            }
        }
    };
}
