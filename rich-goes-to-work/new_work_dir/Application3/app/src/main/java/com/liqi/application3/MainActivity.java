package com.liqi.application3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import cn.finalteam.galleryfinal.permission.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Camera camera = null;
    private static final int GETPERMS = 100;
    private String[] perms;
    private Handler permissionsHandler = new Handler();

    private SurfaceHolder.Callback callback =new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        openCamera();

        }

        private void openCamera() {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int cameraCount = Camera.getNumberOfCameras();


            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                try {
                    camera = Camera.open(0);

                    //mCurrentCamIndex = camIdx;
                } catch (RuntimeException e) {

                }
            }
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.setDisplayOrientation(90);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surface1);
        perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        checkPermission();


    }
    public void checkPermission() {
        //判断是否有相关权限，并申请权限
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            permissionsHandler.post(new Runnable() {
                @Override
                public void run() {
                    surfaceView.getHolder().addCallback(callback);
                }
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, perms, GETPERMS);
        }
    }
}