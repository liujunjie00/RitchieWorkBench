package com.liqi.test3399.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;
import com.liqi.test3399.helper.SystemUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FragmentVersionView extends Fragment {


    public FragmentVersionView() {
    }

    public FragmentVersionView(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView firmwareTextView = view.findViewById(R.id.firmware_version);
        firmwareTextView.setText("固件版本:"+Build.VERSION.RELEASE);

        TextView kernelTextView = view.findViewById(R.id.kernel_version);
        kernelTextView.setText("内核版本："+getFormattedKernelVersion());

        TextView basebandView = view.findViewById(R.id.baseband_version);
        basebandView.setText("基带版本："+SystemUtil.execShellCmd("getprop gsm.version.baseband"));
        TextView product_version = view.findViewById(R.id.product_version);
        product_version.setText("产品版本："+ SystemUtil.execShellCmd("getprop ro.build.description"));
        Log.d("liujunjie","产品版本："+ SystemUtil.execShellCmd("getprop ro.build.description"));


    }

    public void onDestroy() {
        super.onDestroy();
        //取消异步
    }

    private String getFormattedKernelVersion() {
        String procVersionStr;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/version"), 256);
            try {
                procVersionStr = reader.readLine();
            } finally {
                reader.close();
            }

            final String PROC_VERSION_REGEX =
                    "\\w+\\s+" + /* ignore: Linux */
                            "\\w+\\s+" + /* ignore: version */
                            "([^\\s]+)\\s+" + /* group 1: 2.6.22-omap1 */
                            "\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+" + /* group 2: (xxxxxx@xxxxx.constant) */
                            "\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+" + /* ignore: (gcc ..) */
                            "([^\\s]+)\\s+" + /* group 3: #26 */
                            "(?:PREEMPT\\s+)?" + /* ignore: PREEMPT (optional) */
                            "(.+)"; /* group 4: date */

            Pattern p = Pattern.compile(PROC_VERSION_REGEX);
            Matcher m = p.matcher(procVersionStr);

            if (!m.matches()) {

                return "Unavailable";
            } else if (m.groupCount() < 4) {

                return "Unavailable";
            } else {
                return (new StringBuilder(m.group(1)).append("\n").append(
                        m.group(2)).append(" ").append(m.group(3)).append("\n")
                        .append(m.group(4))).toString();
            }
        } catch (IOException e) {


            return "Unavailable";
        }
    }
}
