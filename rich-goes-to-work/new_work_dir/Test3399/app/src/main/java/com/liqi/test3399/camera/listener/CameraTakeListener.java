package com.liqi.test3399.camera.listener;

import android.graphics.Bitmap;

import java.io.File;

/**
 * 图片拍摄回调
 * */
public interface CameraTakeListener {

    void onSuccess(File bitmapFile, Bitmap mBitmap);

    void onFail(String error);

}
