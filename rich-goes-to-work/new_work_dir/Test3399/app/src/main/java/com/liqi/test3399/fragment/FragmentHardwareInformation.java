package com.liqi.test3399.fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.liqi.test3399.R;
import com.liqi.test3399.helper.SystemUtil;
public class FragmentHardwareInformation extends Fragment {
    private Context context;
    private StorageManager mstorageManager;
    public FragmentHardwareInformation(int hardware_information_fragment) {
        super(hardware_information_fragment);
    }

    public FragmentHardwareInformation() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mstorageManager = (StorageManager)context.getSystemService(Context.STORAGE_SERVICE);
        super.onViewCreated(view, savedInstanceState);
        TextView cpu_info = view.findViewById(R.id.cpu_info);
        TextView memory_info =view.findViewById(R.id.memory_info);
        TextView storage_info = view.findViewById(R.id.storage_info);
        String cpuInfo = SystemUtil.execShellCmd("cat /proc/cpuinfo");
        int cpuinfoLsStar = cpuInfo.length();
        cpuInfo=cpuInfo.replace("processor","");
        int cpuinfolsEnd = cpuInfo.length();
        int cpuCore = (cpuinfoLsStar-cpuinfolsEnd)/"processor".length();
        String meninfo = SystemUtil.execShellCmd("cat /proc/meminfo");
        String memory_info_kb = meninfo.substring(meninfo.indexOf(" "),meninfo.indexOf("kB")).trim();
        int memInt = Integer.parseInt(memory_info_kb);
        String memory_info_Mb = formatMemInfo(memInt);
        cpu_info.setText("cpu核心:"+cpuCore+"核");
        memory_info.setText("内存:"+memory_info_Mb);
        String dataTotalSize = getDataTotalSize(context);
        storage_info.setText("存储:"+dataTotalSize);

    }
    private String formatMemInfo(int memInfoKB){
        int memGB = memInfoKB/1024/1024;
        int result = 0;
        int[] arry =  {0,1,2,4,6,8,10,12,14,16,18,20,22,24};
        for (int i = 0; i < arry.length; i++) {
            if (memGB>=arry[i] && memGB<arry[i+1]){
                result = arry[i+1];
            }
        }
        if (memInfoKB>600000&& memInfoKB<900000){
            return  "1.0 GB";
        }
        if (memInfoKB>200000&& memInfoKB<500000){
            return  "0.5 GB";
        }
        if ( result== 0){
            return "无法读到内存信息";
        }
        return result+".0 GB";
    }
    public String getDataTotalSize(Context context){
        StatFs sf = new StatFs(context.getCacheDir().getAbsolutePath());
        long blockSize = sf.getBlockSize();
        long totalBlocks = sf.getBlockCount();
        return FragmentDisk.formatForROMAndRAM(blockSize*totalBlocks);
    }

}
