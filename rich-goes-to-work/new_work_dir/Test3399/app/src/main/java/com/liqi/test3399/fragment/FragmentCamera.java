package com.liqi.test3399.fragment;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import com.liqi.test3399.R;

import java.io.IOException;


public class FragmentCamera extends Fragment {
    int mCurrentCamIndex = 0;
    private SurfaceView sfv_view;
    private Camera camera;
    private Context mContext;
    int cameraCount = 0;
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback(){

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            try {
                    camera = openFrontFacingCameraGingerbread();
                    camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        /**
         * 申请摄像头权限
         * */
        private Camera openFrontFacingCameraGingerbread() {


            Camera cam = null;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();

            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                try {
                    cam = Camera.open(camIdx);
                    mCurrentCamIndex = camIdx;
                } catch (RuntimeException e) {
                }
            }

            return cam;
        }


        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            camera.startPreview();

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            stopPreview();

        }
    };

    public FragmentCamera() {
    }

    private void stopPreview(){
        if (camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        Log.d("liujunjie","结束相机预览");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public FragmentCamera(int  view){
      super(view);
     }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.title);
        sfv_view = view.findViewById(R.id.surfaceview_camera);
        Button button1 = view.findViewById(R.id.camera_btn1);
        Button button2 = view.findViewById(R.id.camera_btn2);
        View layout = view.findViewById(R.id.camera_layout);
         int cameras = Camera.getNumberOfCameras();


        if (cameras ==0){
            sfv_view.setVisibility(View.GONE);
            textView.setText("未找到摄像头");
            textView.setTextSize(30);
            layout.setBackgroundColor(Color.RED);


        }else if(cameras ==1){
            sfv_view.getHolder().addCallback(callback);
            textView.setText("检测到一个摄像头");
        }else if (cameras >1){
            sfv_view.getHolder().addCallback(callback);
            textView.setText("检查到"+cameras+"个摄像头");

        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.RED);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.GREEN);
            }
        });


    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
