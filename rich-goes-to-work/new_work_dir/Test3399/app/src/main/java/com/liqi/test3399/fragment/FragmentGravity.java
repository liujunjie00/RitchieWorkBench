package com.liqi.test3399.fragment;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;

public class FragmentGravity extends Fragment {
    Context context;
    boolean stop =false;
    TextView gravity_textview_x,gravity_textview_y,gravity_textview_z;
    View gravity_layout;

    public FragmentGravity(int gravity_fragment) {
        super(gravity_fragment);
    }

    public FragmentGravity() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SensorManager sensorManager;
        SensorEventListener lsn;
        gravity_textview_x = view.findViewById(R.id.gravity_textview_x);
        gravity_textview_y = view.findViewById(R.id.gravity_textview_y);
        gravity_textview_z = view.findViewById(R.id.gravity_textview_z);
        gravity_layout = view.findViewById(R.id.gravity_layout);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        lsn = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            public void onSensorChanged(SensorEvent e) {
                if(stop) {
                    return;
                }
                   gravity_textview_x.setText("x:"+(double) e.values[0]);
                   gravity_textview_y.setText("y:"+(double) e.values[1]);
                   gravity_textview_z.setText("z:"+(double) e.values[2]);

            }

        };

        Sensor sensors = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(lsn, sensors,
                SensorManager.SENSOR_DELAY_NORMAL);
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,5*1000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stop=true;
    }

    Runnable runnable  = new Runnable() {
        @Override
        public void run() {
            String xx = (String) gravity_textview_x.getText();
            String yy = (String) gravity_textview_y.getText();
            String zz = (String) gravity_textview_z.getText();
            if ((xx+yy+zz).length()<10){
                gravity_layout.setBackgroundColor(Color.RED);

            }
        }
    };

}
